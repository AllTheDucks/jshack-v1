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
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;
import com.alltheducks.bb.stripes.BlackboardActionBeanContext;
import com.alltheducks.bb.stripes.EntitlementRestrictions;
import org.oscelot.jshack.BuildingBlockHelper;
import org.oscelot.jshack.JSHackManager;
import org.oscelot.jshack.JSHackManagerFactory;
import org.oscelot.jshack.model.HackPackage;

/**
 *
 * @author Wiley Fuller <wiley@alltheducks.com>
 */
@EntitlementRestrictions(entitlements={"system.jshacks.MODIFY"}, errorPage="/noaccess.jsp")
public class DeleteHackAction implements ActionBean {


    @Validate(required=true)
    private String hackId;
    private HackPackage hack;
    BlackboardActionBeanContext context;
    
    @DefaultHandler
    public Resolution confirmDelete() throws IOException {
        JSHackManager manager = JSHackManagerFactory.getHackManager();
        hack = manager.getHackById(hackId);
        return new ForwardResolution("/WEB-INF/jsp/confirmDelete.jsp");
    }
    
    public Resolution deleteHackPackage() throws IOException {
        JSHackManager manager = JSHackManagerFactory.getHackManager();
        manager.deletePackage(hackId);
        RedirectResolution redirect = new RedirectResolution("Config.action", false);
        redirect.addParameter(InlineReceiptUtil.SIMPLE_STRING_KEY, BuildingBlockHelper.getLocalisationString("jsh.receipt.hackDeleted"));
        return redirect;
    }
    
    public void setContext(ActionBeanContext context) {
        this.context = (BlackboardActionBeanContext) context;
    }

    public ActionBeanContext getContext() {
        return context;
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

    public HackPackage getHack() {
        return hack;
    }

    public void setHackPackage(HackPackage hackPackage) {
        this.hack = hackPackage;
    }
}
