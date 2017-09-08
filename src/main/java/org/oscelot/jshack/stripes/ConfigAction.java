/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.stripes;

import java.io.IOException;
import java.util.List;

import blackboard.platform.servlet.InlineReceiptUtil;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import com.alltheducks.bb.stripes.BlackboardActionBeanContext;
import com.alltheducks.bb.stripes.EntitlementRestrictions;
import org.oscelot.jshack.BuildingBlockHelper;
import org.oscelot.jshack.JSHackManager;
import org.oscelot.jshack.JSHackManagerFactory;
import org.oscelot.jshack.model.HackConfig;
import org.oscelot.jshack.model.HackPackage;

/**
 *
 * @author Wiley Fuller <wiley@alltheducks.com>
 */
@EntitlementRestrictions(entitlements={"system.jshacks.CREATE"}, errorPage="/noaccess.jsp")
public class ConfigAction implements ActionBean {

    BlackboardActionBeanContext context;
    private List<HackPackage> hackPackages;
    private HackConfig hackConfig;
    
    @DefaultHandler
    public Resolution displayConfigPage() throws IOException {
        JSHackManager manager = JSHackManagerFactory.getHackManager();
        hackPackages = manager.getAllHacks();
        hackConfig = manager.getHackConfig();

        if(hackConfig.isInjectionSuspended()) {
            InlineReceiptUtil.addWarningReceiptToRequest(context.getRequest(), BuildingBlockHelper.getLocalisationString("jsh.receipt.injectionSuspended"));
        }

        return new ForwardResolution("/WEB-INF/jsp/config.jsp");
    }
    
    public void setContext(ActionBeanContext context) {
        this.context = (BlackboardActionBeanContext) context;
    }

    public ActionBeanContext getContext() {
        return context;
    }

    public List<HackPackage> getHackPackages() {
        return hackPackages;
    }

    public void setHackPackages(List<HackPackage> hackPackages) {
        this.hackPackages = hackPackages;
    }

    public HackConfig getHackConfig() {
        return hackConfig;
    }

    public void setHackConfig(HackConfig hackConfig) {
        this.hackConfig = hackConfig;
    }
}
