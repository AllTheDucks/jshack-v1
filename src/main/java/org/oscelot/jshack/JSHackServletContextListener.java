/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author shane
 */
public class JSHackServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
//        try {
//            HackPackage hp = HackPackage.createHackPackage("tag.learningSystemPage.start");
//            hp.setSource("tag.learningSystemPage.start");
//            hp.addHookKey("tag.learningSystemPage.start");
//            
//            hp = HackPackage.createHackPackage("tag.editModeViewToggle.start");
//            hp.setSource("tag.editModeViewToggle.start");
//            hp.addHookKey("tag.editModeViewToggle.start");
//            
//            hp = HackPackage.createHackPackage("jsp.topFrame.start");
//            hp.setSource("jsp.topFrame.start");
//            hp.addHookKey("jsp.topFrame.start");
//            
//            System.out.println("JSHack Initilised");
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
