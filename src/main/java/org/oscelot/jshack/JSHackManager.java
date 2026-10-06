/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack;

import blackboard.platform.context.Context;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.oscelot.jshack.model.*;
import org.oscelot.jshack.model.restrictions.CompiledRestriction;
import org.oscelot.jshack.model.restrictions.URLRestriction;

/**
 *
 * @author Wiley Fuller <wiley@alltheducks.com>
 */
public class JSHackManager {

    private static final Logger LOG = Logger.getLogger(JSHackManager.class.getName());

    public static final String HACKPACKAGE_FILENAME = "hackpackage.xml";
    public static final String SNIPPET_FILENAME = "snippet.html";
    private File hacksRoot;
    private File workingDirectory;
    private File archiveDirectory;
    private File hackConfigFile;
    private HackConfig hackConfig;
    private Date lastPackageReload;
    private volatile List<HackPackage> hackPackages;
    private HashMap<String, List<HackPackageReference>> hackPackageReferences;

    private final CompiledRestriction SKIP_CONFIG_PAGE_RESTRICTION;

    private Comparator<HackPackage> nameComparator = Comparator.comparing(HackPackage::getName);
    private Comparator<HackPackage> priorityComparator = Comparator.comparing(HackPackage::getPriority).reversed();

    public JSHackManager() {
        final Restriction restriction = new Restriction();
        restriction.setType(RestrictionType.URL);
        restriction.setValue(".*/webapps/oslt-jshack-.*");
        restriction.setInverse(true);
        SKIP_CONFIG_PAGE_RESTRICTION = RestrictionCompiler.compileRestriction(restriction);
    }

    public List<HackPackage> getMatchingHacks(String key, Context context) throws IOException {
        List<HackPackageReference> packageRefs = getHackPackageReferences(key);

        if (packageRefs == null) {
            return null;
        }

        List<HackPackage> matchingPackages = new ArrayList<HackPackage>();

        for (HackPackageReference p : packageRefs) {

            final ArrayList<CompiledRestriction> restrictions = new ArrayList<>(p.getCompiledRestrictions());
            restrictions.add(SKIP_CONFIG_PAGE_RESTRICTION);

            // Check all restrictions
            boolean passedRestrictions = true;
            for (CompiledRestriction cr : restrictions) {
                passedRestrictions &= cr.test(context) ^ cr.isInverse();
                if (!passedRestrictions) {
                    break;
                }
            }

            if (passedRestrictions) {
                matchingPackages.add(p.getHackPackage());
            }
        }

        matchingPackages.sort(priorityComparator);

        return matchingPackages;
    }

    public List<HackPackage> getAllHacks() throws IOException {
        return getHackPackages();
    }

    /**
     *
     */
    public List<HackPackage> loadHackPackages() throws IOException {
        hackConfig = readHackConfig();

        ArrayList<HackPackage> reloadedPackages = new ArrayList<HackPackage>();
        hackPackageReferences = new HashMap<String, List<HackPackageReference>>();

        String children[] = hacksRoot.list();
        for (String childPath : children) {
            File child = new File(hacksRoot, childPath);
            if (!child.isDirectory()) {
                continue;
            }
            File packageManifest = new File(child, HACKPACKAGE_FILENAME);
            File snippetFile = new File(child, SNIPPET_FILENAME);
            if (packageManifest.exists()) {
                XStream xstream = getHackPackageXstream();
                HackPackage hackPackage;
                try {
                    hackPackage = (HackPackage) xstream.fromXML(packageManifest);
                } catch (RuntimeException ex) {
                    // One bad manifest should not take down every other hack.
                    LOG.log(Level.SEVERE, "Skipping unreadable hack package manifest ["
                            + packageManifest.getAbsolutePath() + "]", ex);
                    continue;
                }
                hackPackage.setSnippet(FileUtils.readFileToString(snippetFile, "UTF-8"));
                hackPackage.setEnabled(hackConfig.isPackageEnabled(hackPackage.getIdentifier()));

                reloadedPackages.add(hackPackage);

                if (hackPackage.isEnabled()) {
                    HackPackageReference hackPackageReference = new HackPackageReference();
                    hackPackageReference.setHackPackage(hackPackage);

                    List<HackPackageReference> HookSpecificList;
                    if (hackPackageReferences.containsKey(hackPackage.getHook())) {
                        HookSpecificList = hackPackageReferences.get(hackPackage.getHook());
                    } else {
                        HookSpecificList = new ArrayList<HackPackageReference>();
                        hackPackageReferences.put(hackPackage.getHook(), HookSpecificList);
                    }
                    HookSpecificList.add(hackPackageReference);
                }
            }
        }

        reloadedPackages.sort(nameComparator);

        hackPackages = reloadedPackages;
        lastPackageReload = new Date();
        return reloadedPackages;
    }

    /**
     * @return the hacksRoot
     */
    public File getHacksRoot() {
        return hacksRoot;
    }

    /**
     * @param hacksRoot the hacksRoot to set
     */
    public void setHacksRoot(File hacksRoot) {
        this.hacksRoot = hacksRoot;
    }

    /**
     * Finds the HackPackage with matching ID, and returns it, otherwise returns
     * null
     *
     * @param hackId
     * @return
     */
    public HackPackage getHackById(String hackId) throws IOException {
        getHackPackages();
        for (HackPackage hackPackage : hackPackages) {
            if (hackPackage.getIdentifier().equals(hackId)) {
                return hackPackage;
            }
        }
        return null;
    }

    public void persistPackage(HackPackage hack) throws IOException {
        File hackDir = new File(hacksRoot, hack.getIdentifier());
        if (!hackDir.exists()) {
            hackDir.mkdir();
        }
        File hackPackageFile = new File(hackDir, HACKPACKAGE_FILENAME);
        File snippetFile = new File(hackDir, SNIPPET_FILENAME);

        FileUtils.writeStringToFile(snippetFile, hack.getSnippet(), "UTF-8");

        writeXmlAtomically(hackPackageFile, getHackPackageXstream(), hack);

        flagToReloadPackages();
    }

    public void persistHackConfig() throws IOException {
        writeXmlAtomically(this.hackConfigFile, this.getHackConfigXstream(), this.hackConfig);

        this.flagToReloadPackages();
    }

    /**
     * Reads hackConfig.xml, falling back to a freshly written default if it is missing,
     * empty or unparseable. Without this a zero byte config is never replaced, and the
     * building block stays dead.
     */
    private HackConfig readHackConfig() throws IOException {
        if (hackConfigFile.isFile() && hackConfigFile.length() > 0) {
            try {
                return (HackConfig) getHackConfigXstream().fromXML(hackConfigFile);
            } catch (RuntimeException ex) {
                LOG.log(Level.SEVERE, "Could not read hack config ["
                        + hackConfigFile.getAbsolutePath() + "], resetting it to defaults. "
                        + "Hacks will need to be re-enabled.", ex);
            }
        } else if (hackConfigFile.exists()) {
            LOG.log(Level.SEVERE, "Hack config [" + hackConfigFile.getAbsolutePath()
                    + "] is empty, resetting it to defaults. Hacks will need to be re-enabled.");
        }

        HackConfig replacement = new HackConfig();
        writeXmlAtomically(hackConfigFile, getHackConfigXstream(), replacement);
        return replacement;
    }

    /**
     * Marshals to a temporary file and renames, so a failed write cannot leave the target
     * truncated. Writing straight to a FileOutputStream empties it first, and anything
     * thrown after that point leaves an unreadable zero byte document behind.
     */
    static void writeXmlAtomically(File target, XStream xstream, Object graph) throws IOException {
        File temp = File.createTempFile(target.getName(), ".tmp", target.getParentFile());
        try {
            try (FileOutputStream out = new FileOutputStream(temp)) {
                xstream.toXML(graph, out);
                out.flush();
            }
            try {
                Files.move(temp.toPath(), target.toPath(),
                        StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(temp.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temp.toPath());
        }
    }

    public XStream getHackPackageXstream() {
        XStream xstream = new XStream(new DomDriver("UTF-8"));
        xstream.allowTypesByWildcard(new String[] {
                "org.oscelot.jshack.model.*"
        });
        xstream.alias("hackpackage", HackPackage.class);

        ClassAliasingMapper mapper = new ClassAliasingMapper(xstream.getMapper());
        mapper.addClassAlias("pattern", String.class);
        xstream.registerLocalConverter(HackPackage.class, "urlPatterns", new CollectionConverter(mapper));

        xstream.alias("restriction", Restriction.class);
        xstream.aliasField("restrictions", HackPackage.class, "restrictions");

        xstream.omitField(HackPackage.class, "snippet");
        xstream.omitField(HackPackage.class, "enabled");

        xstream.alias("resource", HackResource.class);
        return xstream;
    }

    public XStream getHackConfigXstream() {
        XStream xstream = new XStream(new DomDriver("UTF-8"));
        xstream.allowTypesByWildcard(new String[] {
                "org.oscelot.jshack.model.*"
        });
        xstream.alias("hackconfig", HackConfig.class);

        ClassAliasingMapper mapper = new ClassAliasingMapper(xstream.getMapper());
        mapper.addClassAlias("package", String.class);
        xstream.registerLocalConverter(HackConfig.class, "enabledPackages", new CollectionConverter(mapper));

        return xstream;
    }

    private List<HackPackage> getHackPackages() throws IOException {
        reloadPackagesIfRequired();
        return hackPackages;
    }

    private List<HackPackageReference> getHackPackageReferences(String hookKey) throws IOException {
        reloadPackagesIfRequired();
        return hackPackageReferences.get(hookKey);
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public void deletePackage(String identifier) throws IOException {
        this.disableHack(identifier);

        File packageDir = new File(getHacksRoot(), identifier);
        File archiveDir = new File(getArchiveDirectory().getPath() + File.separator + identifier + "-" + System.currentTimeMillis());
        if (!archiveDir.exists()) {
            packageDir.renameTo(archiveDir);
        }

        flagToReloadPackages();
    }

    public void flagToReloadPackages() {
        try {
            FileUtils.touch(hackConfigFile);
        } catch (IOException ex) {
            Logger.getLogger(JSHackManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Failed to flag packages for reloading", ex);
        }
    }

    public boolean packagesRequireReloading() {
        return lastPackageReload == null
                || hackPackages == null
                || hackPackageReferences == null
                || hackConfig == null
                || FileUtils.isFileNewer(hackConfigFile, lastPackageReload);
    }

    public void reloadPackagesIfRequired() throws IOException {
        if (packagesRequireReloading()) {
            synchronized (this) {
                if (packagesRequireReloading()) {
                    loadHackPackages();
                }
            }
        }
    }

    public File getHackConfigFile() {
        return hackConfigFile;
    }

    public void setHackConfigFile(File hackConfigFile) {
        this.hackConfigFile = hackConfigFile;
    }

    public HackConfig getHackConfig() throws IOException {
        reloadPackagesIfRequired();
        return hackConfig;
    }

    public void enableHack(HackPackage hackPackage) throws IOException {
        this.enableHack(hackPackage.getIdentifier());
    }

    public void enableHack(String hackPackageIdentifer) throws IOException {
        getHackConfig().setPackageEnabled(hackPackageIdentifer);
        this.persistHackConfig();
    }

    public void disableHack(HackPackage hackPackage) throws IOException {
        this.enableHack(hackPackage.getIdentifier());
    }

    public void disableHack(String hackPackageIdentifer) throws IOException {
        getHackConfig().setPackageDisabled(hackPackageIdentifer);
        this.persistHackConfig();
    }

    public void suspendInjection() throws IOException {
        getHackConfig().setInjectionSuspended(true);
        this.persistHackConfig();
    }

    public void resumeInjection() throws IOException {
        getHackConfig().setInjectionSuspended(false);
        this.persistHackConfig();
    }

    public File getArchiveDirectory() {
        return archiveDirectory;
    }

    public void setArchiveDirectory(File archiveDirectory) {
        this.archiveDirectory = archiveDirectory;
    }
}
