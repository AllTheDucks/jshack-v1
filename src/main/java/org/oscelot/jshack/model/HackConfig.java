/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shane Argo <sargo@usc.edu.au>
 */
public class HackConfig {
    
    private List<String> enabledPackages;
    private boolean injectionSuspended;

    public List<String> getEnabledPackages() {
        return enabledPackages;
    }

    public void setEnabledPackages(List<String> availablePackages) {
        this.enabledPackages = availablePackages;
    }

    public boolean isInjectionSuspended() {
        return injectionSuspended;
    }

    public void setInjectionSuspended(boolean injectionSuspended) {
        this.injectionSuspended = injectionSuspended;
    }

    public boolean isPackageEnabled(String identifier) {
        if(enabledPackages == null) {
            return false;
        }
        return enabledPackages.contains(identifier);
    }
    
    public void setPackageDisabled(String identifier) {
        if(isPackageEnabled(identifier)) {
            enabledPackages.remove(identifier);
        }
    }
    
    public void setPackageEnabled(String identifier) {
        if(!isPackageEnabled(identifier)) {
            if(enabledPackages == null) {
                enabledPackages = new ArrayList<String>();
            }
            enabledPackages.add(identifier);
        }
    }
    
    
}
