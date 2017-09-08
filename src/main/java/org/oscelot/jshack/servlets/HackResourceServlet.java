/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.oscelot.jshack.JSHackManager;
import org.oscelot.jshack.JSHackManagerFactory;
import org.oscelot.jshack.model.HackPackage;
import org.oscelot.jshack.model.HackResource;

/**
 *
 * @author Wiley Fuller <wiley@alltheducks.com>
 */
public class HackResourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSHackManager manager = JSHackManagerFactory.getHackManager();
        File hackRoot = manager.getHacksRoot();

        String pathInfo = request.getPathInfo();
        
        if (pathInfo.contains("..")) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        String[] pathSegments = pathInfo.substring(1).split("/", 3);

        if (pathSegments.length != 3) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String hackId = pathSegments[0];
        String timestamp = pathSegments[1];
        String resourcePath = pathSegments[2];

        HackPackage hack = null;
        hack = manager.getHackById(hackId);

        if (hack == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (hack.getResources() == null || hack.getResources().size() == 0) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        for (HackResource hackResource : hack.getResources()) {
            if (hackResource.getPath().equals(resourcePath.trim())) {
                File resourceFile = new File(hackRoot, hackId + File.separator + "resources" + File.separator + resourcePath);
                if (!resourceFile.exists()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                response.setContentType(hackResource.getMime().trim());
                FileInputStream fis = new FileInputStream(resourceFile);
                IOUtils.copy(fis, response.getOutputStream());
                fis.close();
                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
