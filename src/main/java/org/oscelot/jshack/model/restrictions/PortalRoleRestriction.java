package org.oscelot.jshack.model.restrictions;

import blackboard.data.role.PortalRole;
import blackboard.persist.Id;
import blackboard.persist.PersistenceException;
import blackboard.persist.role.PortalRoleDbLoader;
import blackboard.platform.context.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Shane Argo <shane@alltheducks.com>
 */
public class PortalRoleRestriction extends CompiledRestriction {

    private List<PortalRole> roles;
    
    @Override
    public boolean test(final Context context) {
        final Id userId = context.getUserId();

        if(userId == null) {
            return false;
        }

        final List<PortalRole> actualRoles;
        try {
            actualRoles = PortalRoleDbLoader.Default.getInstance().loadAllByUserId(userId);
        } catch (PersistenceException ex) {
            Logger.getLogger(PortalRoleRestriction.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        for(final PortalRole requiredRole : roles) {
            for(final PortalRole actualRole : actualRoles) {
                if(actualRole.getId().equals(requiredRole.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public int getPriority() {
        return 6;
    }

    public List<PortalRole> getRoles() {
        return roles;
    }

    public void setRoles(List<PortalRole> roles) {
        this.roles = roles;
    }
    
    public void setRolesByPattern(Pattern pattern) throws PersistenceException {
        final PortalRoleDbLoader loader = PortalRoleDbLoader.Default.getInstance();
        roles = new ArrayList<>();
        for(final PortalRole pr : loader.loadAll()) {
            final Matcher m = pattern.matcher(pr.getRoleID());
            if(m.matches()) {
                roles.add(pr);
            }
        }
    }
    
    public void setRolesByPatternString(String patternString) throws PersistenceException {
        this.setRolesByPattern(Pattern.compile(patternString));
    }
    
}
