/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.stripes;

import blackboard.platform.servlet.InlineReceiptUtil;
import java.io.IOException;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;
import com.alltheducks.bb.stripes.BlackboardActionBeanContext;
import com.alltheducks.bb.stripes.EntitlementRestrictions;
import org.oscelot.jshack.BuildingBlockHelper;
import org.oscelot.jshack.JSHackManager;
import org.oscelot.jshack.JSHackManagerFactory;

/**
 *
 * @author Shane Argo <shane@alltheducks.com>
 */
@EntitlementRestrictions(entitlements={"system.jshacks.MODIFY"}, errorPage="/noaccess.jsp")
public class ReloadHackPackagesAction implements ActionBean {


    @Validate(required=true)
    BlackboardActionBeanContext context;
    
    @DefaultHandler
    @DontValidate
    public Resolution reloadHackPackages() throws IOException {
        JSHackManager manager = JSHackManagerFactory.getHackManager();
        manager.flagToReloadPackages();
        RedirectResolution redirect = new RedirectResolution("Config.action", false);
        redirect.addParameter(InlineReceiptUtil.SIMPLE_STRING_KEY, BuildingBlockHelper.getLocalisationString("jsh.receipt.packagesReloaded"));
        return redirect;
    }
    
    public void setContext(ActionBeanContext context) {
        this.context = (BlackboardActionBeanContext) context;
    }

    public ActionBeanContext getContext() {
        return context;
    }

}
