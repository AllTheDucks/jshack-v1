/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack;

import blackboard.platform.plugin.PlugInException;
import blackboard.platform.plugin.PlugInUtil;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.oscelot.jshack.model.HackConfig;

/**
 *
 * @author Wiley Fuller <wiley@alltheducks.com>
 */
public class JSHackManagerFactory {
    
    private static JSHackManager hackManagerInstance;
    public static final String HACKS_DIR_NAME = "hacks";
    public static final String WORKING_DIR_NAME = "working";
    public static final String ARCHIVE_DIR_NAME = "archive";
    public static final String HACK_CONFIG_FILE_NAME = "hackConfig.xml";
    
    public static synchronized JSHackManager getHackManager() {
        if (hackManagerInstance == null) {
            File configDir;
            try {
                configDir = PlugInUtil.getConfigDirectory("oslt", "jshack");
            } catch (PlugInException ex) {
                /* if we get a PluginException, there's nothing we can do 
                 * just log it, wrap it, and rethrow it. */
                Logger.getLogger(JSHackManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Problem while trying to get Building Block Config Directory", ex);
            }
            
            hackManagerInstance = new JSHackManager();
            
            File hacksRoot = new File(configDir, HACKS_DIR_NAME);
            if (!hacksRoot.exists()) {
                hacksRoot.mkdir();
            }
            File workingDirectory = new File(configDir, WORKING_DIR_NAME);
            if (!workingDirectory.exists()) {
                workingDirectory.mkdir();
            }
            File archiveDirectory = new File(configDir, ARCHIVE_DIR_NAME);
            if (!archiveDirectory.exists()) {
                archiveDirectory.mkdir();
            }
            File hackConfigFile = new File(configDir, HACK_CONFIG_FILE_NAME);
            if (!hackConfigFile.exists()) {
                try {
                    FileOutputStream hackConfigOut = new FileOutputStream(hackConfigFile);

                    XStream xstream = hackManagerInstance.getHackConfigXstream();
                    xstream.toXML(new HackConfig(), hackConfigOut);
                    hackConfigOut.close();
                } catch (IOException ex) {
                    Logger.getLogger(JSHackManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException("Problem while trying to create hackConfig.xml", ex);
                }
            }
            hackManagerInstance.setHacksRoot(hacksRoot);
            hackManagerInstance.setWorkingDirectory(workingDirectory);
            hackManagerInstance.setArchiveDirectory(archiveDirectory);
            hackManagerInstance.setHackConfigFile(hackConfigFile);
        }
        return hackManagerInstance;
    }
}
