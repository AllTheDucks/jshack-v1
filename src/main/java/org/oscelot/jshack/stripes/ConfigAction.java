/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.stripes;

import java.io.IOException;
import java.util.List;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import com.alltheducks.bb.stripes.BlackboardActionBeanContext;
import com.alltheducks.bb.stripes.EntitlementRestrictions;
import org.oscelot.jshack.JSHackManager;
import org.oscelot.jshack.JSHackManagerFactory;
import org.oscelot.jshack.model.HackPackage;

/**
 *
 * @author Wiley Fuller <wfuller@swin.edu.au>
 */
@EntitlementRestrictions(entitlements={"system.jshacks.CREATE"}, errorPage="/noaccess.jsp")
public class ConfigAction implements ActionBean {

    BlackboardActionBeanContext context;
    private List<HackPackage> hackPackages;
    
    @DefaultHandler
    public Resolution displayConfigPage() throws IOException {
        JSHackManager manager = JSHackManagerFactory.getHackManager();
        hackPackages = manager.getAllHacks();
        return new ForwardResolution("/WEB-INF/jsp/config.jsp");
    }
    
    public void setContext(ActionBeanContext context) {
        this.context = (BlackboardActionBeanContext) context;
    }

    public ActionBeanContext getContext() {
        return context;
    }

    /**
     * @return the hackPackages
     */
    public List<HackPackage> getHackPackages() {
        return hackPackages;
    }

    /**
     * @param hackPackages the hackPackages to set
     */
    public void setHackPackages(List<HackPackage> hackPackages) {
        this.hackPackages = hackPackages;
    }
    
}
