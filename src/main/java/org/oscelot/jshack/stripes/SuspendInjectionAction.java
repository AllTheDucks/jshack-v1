/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.stripes;

import blackboard.platform.servlet.InlineReceiptUtil;
import com.alltheducks.bb.stripes.BlackboardActionBeanContext;
import com.alltheducks.bb.stripes.EntitlementRestrictions;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import org.oscelot.jshack.BuildingBlockHelper;
import org.oscelot.jshack.JSHackManager;
import org.oscelot.jshack.JSHackManagerFactory;

import java.io.IOException;

/**
 *
 * @author Shane Argo <sargo@usc.edu.au>
 */
@EntitlementRestrictions(entitlements={"system.jshacks.CREATE"}, errorPage="/noaccess.jsp")
public class SuspendInjectionAction implements ActionBean {


    BlackboardActionBeanContext context;
    
    public Resolution suspend() throws IOException {
        JSHackManager manager = JSHackManagerFactory.getHackManager();

        manager.suspendInjection();

        return new RedirectResolution("Config.action", false);
    }

    public Resolution resume() throws IOException {
        JSHackManager manager = JSHackManagerFactory.getHackManager();

        manager.resumeInjection();

        final String url = InlineReceiptUtil.addSuccessReceiptToUrl("Config.action", BuildingBlockHelper.getLocalisationString("jsh.receipt.injectionResumed"));
        return new RedirectResolution(url, false);
    }
    
    public void setContext(ActionBeanContext context) {
        this.context = (BlackboardActionBeanContext) context;
    }

    public ActionBeanContext getContext() {
        return context;
    }

}
