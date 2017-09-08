/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.model;

import java.util.List;
import org.oscelot.jshack.RestrictionCompiler;
import org.oscelot.jshack.model.restrictions.CompiledRestriction;

/**
 *
 * @author Shane Argo <shane@alltheducks.com>
 */
public class HackPackageReference {
    
    private HackPackage hackPackage;
    private List<CompiledRestriction> compiledRestrictions;

    public HackPackage getHackPackage() {
        return hackPackage;
    }

    public void setHackPackage(HackPackage hackPackage) {
        this.hackPackage = hackPackage;
        this.compiledRestrictions = RestrictionCompiler.compileRestrictions(hackPackage.getRestrictions());
    }

    public List<CompiledRestriction> getCompiledRestrictions() {
        return compiledRestrictions;
    }
    
}
