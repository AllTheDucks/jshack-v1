/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.stripes;

import java.io.IOException;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;
import org.oscelot.bb.stripes.BlackboardActionBeanContext;
import org.oscelot.bb.stripes.EntitlementRestrictions;
import org.oscelot.jshack.HackPackager;
import org.oscelot.jshack.exceptions.PackageNotFoundException;

/**
 *
 * @author Shane Argo <sargo@usc.edu.au>
 */
@EntitlementRestrictions(entitlements={"system.jshacks.CREATE"}, errorPage="/noaccess.jsp")
public class DownloadHackPackageAction implements ActionBean {

    @Validate(required=true)
    private String hackId;
    BlackboardActionBeanContext context;

    @DefaultHandler
    @DontValidate
    public Resolution downloadHackPackage() throws IOException, PackageNotFoundException {
        HackPackager.packageHack(hackId, context.getResponse());
        return null;
    }

    public void setContext(ActionBeanContext context) {
        this.context = (BlackboardActionBeanContext) context;
    }

    public ActionBeanContext getContext() {
        return context;
    }

    public String getHackId() {
        return hackId;
    }

    public void setHackId(String hackId) {
        this.hackId = hackId;
    }

}
