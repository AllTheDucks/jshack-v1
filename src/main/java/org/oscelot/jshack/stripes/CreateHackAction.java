/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.stripes;

import blackboard.platform.servlet.InlineReceiptUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationMethod;
import net.sourceforge.stripes.validation.ValidationState;
import org.apache.commons.io.FileUtils;
import com.alltheducks.bb.stripes.BlackboardActionBeanContext;
import com.alltheducks.bb.stripes.EntitlementRestrictions;
import org.oscelot.jshack.BuildingBlockHelper;
import org.oscelot.jshack.JSHackManager;
import org.oscelot.jshack.JSHackManagerFactory;
import org.oscelot.jshack.model.HackPackage;
import org.oscelot.jshack.model.HackResource;
import org.oscelot.jshack.model.Restriction;

/**
 *
 * @author Wiley Fuller <wfuller@swin.edu.au>
 */
@EntitlementRestrictions(entitlements={"system.jshacks.CREATE"}, errorPage="/noaccess.jsp")
public class CreateHackAction implements ActionBean {

    private static final String DEFAULT_SNIPPET = "<!-- This snippet binds a function to the \"dom:loaded\" event.\n"
            + "You can put your own javascript into the function, or replace\n"
            + "the entire snippet. -->\n"
            + "<script type=\"text/javascript\">\n"
            + "    Event.observe(document,\"dom:loaded\", function() {\n"
            + "        //Your javascript goes here. \n"
            + "    });\n"
            + "</script>\n";
    BlackboardActionBeanContext context;
    @ValidateNestedProperties({
        @Validate(field = "identifier", required = true),
        @Validate(field = "name", required = true),
        @Validate(field = "description", required = true),
        @Validate(field = "snippet", converter = Base64StringConverter.class),
        @Validate(field = "resources.mime", converter = Base64StringConverter.class)
    })
    private HackPackage hack;
    private JSHackManager manager;
    private String hackId;
    private List<FileBean> resourceFiles;
    private List<String> tempFiles;

    @Before(stages = LifecycleStage.BindingAndValidation)
    public void init() {
        manager = JSHackManagerFactory.getHackManager();
        
    }

    @DefaultHandler
    @DontValidate
    public Resolution displayCreateHackPage() throws IOException {
        manager = JSHackManagerFactory.getHackManager();
        if (hackId == null || hackId.isEmpty()) {
            hack = new HackPackage();
        } else {
            hack = manager.getHackById(hackId);
        }
        if (hack.getIdentifier() == null || hack.getIdentifier().isEmpty()) {
            hack.setSnippet(DEFAULT_SNIPPET);
        }

        return new ForwardResolution("/WEB-INF/jsp/createHack.jsp");
    }

    @Before(on = {"saveHackPackage"}, stages = LifecycleStage.EventHandling)
    public void postProcessHack() {

        ArrayList<HackResource> tempResources = new ArrayList<HackResource>();
        if (hack.getResources() != null) {
            for (HackResource hackResource : hack.getResources()) {
                if (hackResource.getPath() != null && !hackResource.getPath().isEmpty()) {
                    tempResources.add(hackResource);
                }
            }
        }
        hack.setResources(tempResources);

        ArrayList<Restriction> tempRestrictions = new ArrayList<Restriction>();
        if (hack.getRestrictions() != null) {
            for (Restriction restriction : hack.getRestrictions()) {
                if (restriction.getValue() != null && !restriction.getValue().isEmpty()) {
                    tempRestrictions.add(restriction);
                }
            }
        }
        hack.setRestrictions(tempRestrictions);

    }

    @ValidationMethod(when = ValidationState.ALWAYS)
    public void preProcessUploadedFiles() throws IOException {

        if (tempFiles == null) {
            tempFiles = new ArrayList<String>();
        }
        for (int i = tempFiles.size(); i < hack.getResources().size(); i++) {
            tempFiles.add(null);
        }

        if (resourceFiles != null) {
            for (int i = 0; i < resourceFiles.size(); i++) {
                FileBean currFile = resourceFiles.get(i);
                HackResource resource = hack.getResources().get(i);
                if (currFile != null) {
                    resource.setPath(currFile.getFileName());
                    File tempFile = File.createTempFile("resource", ".dat", manager.getWorkingDirectory());
                    currFile.save(tempFile);
                    tempFiles.set(i, tempFile.getName());
                }
            }
        }
    }

    public Resolution saveHackPackage() throws IOException {

        File hackRoot = new File(manager.getHacksRoot(), hack.getIdentifier());
        File resourceDirectory = new File(hackRoot, "resources");
        for(int i = 0; i < hack.getResources().size(); i++) {
            if (tempFiles.get(i) != null) {
                File tempFile = new File(manager.getWorkingDirectory(),tempFiles.get(i));
                File destFile = new File(resourceDirectory,hack.getResources().get(i).getPath());
                FileUtils.copyFile(tempFile, destFile);
                tempFile.delete();
            }
        }
        
        manager.persistPackage(hack);

        RedirectResolution redirect = new RedirectResolution("Config.action", false);
        redirect.addParameter(InlineReceiptUtil.SIMPLE_STRING_KEY, BuildingBlockHelper.getLocalisationString("jsh.receipt.hackSaved"));
        return redirect;
    }

    public void setContext(ActionBeanContext context) {
        this.context = (BlackboardActionBeanContext) context;
    }

    public ActionBeanContext getContext() {
        return context;
    }

    /**
     * @return the hack
     */
    public HackPackage getHack() {
        return hack;
    }

    /**
     * @param hack the hack to set
     */
    public void setHack(HackPackage hack) {
        this.hack = hack;
    }

    /**
     * @return the hackId
     */
    public String getHackId() {
        return hackId;
    }

    /**
     * @param hackId the hackId to set
     */
    public void setHackId(String hackId) {
        this.hackId = hackId;
    }

    /**
     * @return the resourceFiles
     */
    public List<FileBean> getResourceFiles() {
        return resourceFiles;
    }

    /**
     * @param resourceFiles the resourceFiles to set
     */
    public void setResourceFiles(List<FileBean> resourceFiles) {
        this.resourceFiles = resourceFiles;
    }

    /**
     * @return the tempFiles
     */
    public List<String> getTempFiles() {
        return tempFiles;
    }

    /**
     * @param tempFiles the tempFiles to set
     */
    public void setTempFiles(List<String> tempFiles) {
        this.tempFiles = tempFiles;
    }
}
