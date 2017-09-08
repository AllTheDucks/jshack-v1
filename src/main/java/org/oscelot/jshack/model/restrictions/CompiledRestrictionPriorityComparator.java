/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.model.restrictions;

import java.util.Comparator;

/**
 *
 * @author Shane Argo <shane@alltheducks.com>
 */
public class CompiledRestrictionPriorityComparator implements Comparator<CompiledRestriction> {

    public int compare(CompiledRestriction o1, CompiledRestriction o2) {
        return o1.getPriority() - o2.getPriority();
    }
    
}
