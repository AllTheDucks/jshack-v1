/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.hooks;

import blackboard.platform.context.Context;
import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.plugin.PlugInUtil;
import blackboard.servlet.renderinghook.RenderingHook;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.oscelot.jshack.BuildingBlockHelper;
import org.oscelot.jshack.JSHackManager;
import org.oscelot.jshack.JSHackManagerFactory;
import org.oscelot.jshack.model.HackPackage;

/**
 * Base class for the JSHack rendering hook implementations.
 *
 * @author Wiley Fuller <wiley@alltheducks.com>
 */
public abstract class JSHackRenderingHook implements RenderingHook {

    @Override
    public abstract String getKey();
    protected JSHackManager hackManager;
    private VelocityEngine ve;

    @Override
    public String getContent() {
        HackPackage currPkg = null;
        try {
            hackManager = getHackManager();

            if(hackManager.getHackConfig().isInjectionSuspended()) {
                return "<!-- JS Hack injection has been suspended -->";
            }

            final StringBuilder output = new StringBuilder();

            final Context context = ContextManagerFactory.getInstance().getContext();
            if (context == null) {
                return "";
            }

            final List<HackPackage> packages = hackManager.getMatchingHacks(this.getKey(), context);

            if (packages != null) {
                for (HackPackage hackPackage : packages) {
                    currPkg = hackPackage;

                    final StringBuilder sb = new StringBuilder("\n<!-- START HACK : ");
                    sb.append(hackPackage.getIdentifier());
                    sb.append(" -->\n");
                    try {
                        sb.append(renderSnippet(hackPackage, hackPackage.getSnippet(), context));
                    } catch (Exception e) {
                        sb.append("\n<!-- Error rendering hack: \n");
                        sb.append(e.getMessage());
                        sb.append(" -->\n");

                    }
                    sb.append("\n<!-- END HACK : ");
                    sb.append(hackPackage.getIdentifier());
                    sb.append(" -->\n");
                    output.append(sb.toString());
                }
            }

            return output.toString();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.SEVERE, 
                    "Exception while processing JS Hacks. Problem with Package: " +
                    (currPkg == null ? "UNKNOWN" : currPkg.getIdentifier()), ex);
            ex.printStackTrace();
            return "<!-- ERROR RENDERING JS HACK - See Logs for details. -->";
        }
    }

    /**
     * Gets the current HackManager instance, or if null, gets an instance from
     * the Factory.
     *
     * @return the current HackManager instance.
     */
    protected JSHackManager getHackManager() {
        if (hackManager == null) {
            hackManager = JSHackManagerFactory.getHackManager();
        }
        return hackManager;
    }
    
    protected String renderSnippet(HackPackage hack, String snippet, Context context) {
        if (ve == null) {
            ve = createVelocityEngine();
        }
        VelocityContext vc = new VelocityContext();
        String baseUrl = PlugInUtil.getUriStem(BuildingBlockHelper.VENDOR_ID, BuildingBlockHelper.HANDLE);
        long lastModified = hackManager.getHackConfigFile().lastModified();
        String resourcePath = baseUrl+"resources/"+hack.getIdentifier()+"/"+lastModified;
        vc.put("resourcePath", resourcePath);
        vc.put("context", context);
        StringWriter sw = new StringWriter();

        try {
            ve.evaluate(vc, sw, "SnippetHtmlString", snippet);
        } catch (IOException ex) {
            Logger.getLogger(JSHackRenderingHook.class.getName()).log(Level.SEVERE, null, ex);
            return "Error in JSHackRenderingHook. See Log for details. (" + ex.getMessage() + ")";
        }
        return sw.toString();
    }

    public VelocityEngine getVelocityEngine() {
      return ve;
    }

    private synchronized VelocityEngine createVelocityEngine() {
        if (ve == null) {
            ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                "org.oscelot.jshack.Slf4jLogChute" );
        }
        return ve;
    }

    public void setVelocityEngine(VelocityEngine ve) {
        this.ve = ve;
    }
}