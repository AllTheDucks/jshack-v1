/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author sargo
 */
public class HackPackage {
 
    private String name;
    private String identifier;
    private String description;
    private String version;
    private String targetVersionMin;
    private String targetVersionMax;
    private String developerName;
    private String developerInstitution;
    private String developerURL;
    private String developerEmail;
    private String hook;
    private List<Restriction> restrictions;// = new ArrayList<Restriction>();
    private List<HackResource> resources;// = new ArrayList<HackResource>();
    private String snippet;
    private boolean enabled;
    private String source;
    private Date lastUpdated;
    private int priority;
    
    public HackPackage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetVersionMin() {
        return targetVersionMin;
    }

    public void setTargetVersionMin(String targetVersionMin) {
        this.targetVersionMin = targetVersionMin;
    }

    public String getTargetVersionMax() {
        return targetVersionMax;
    }

    public void setTargetVersionMax(String targetVersionMax) {
        this.targetVersionMax = targetVersionMax;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

   

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    /**
     * @return the hook
     */
    public String getHook() {
        return hook;
    }

    /**
     * @param hook the hook to set
     */
    public void setHook(String hook) {
        this.hook = hook;
    }

    /**
     * @return the restrictions
     */
    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    /**
     * @param restrictions the restrictions to set
     */
    public void setRestrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * @return the resources
     */
    public List<HackResource> getResources() {
        return resources;
    }

    /**
     * @param resources the resources to set
     */
    public void setResources(List<HackResource> resources) {
        this.resources = resources;
    }

    /**
     * @return the lastUpdated
     */
    public Date getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getDeveloperURL() {
        return developerURL;
    }

    public void setDeveloperURL(String developerURL) {
        this.developerURL = developerURL;
    }

    public String getDeveloperEmail() {
        return developerEmail;
    }

    public void setDeveloperEmail(String developerEmail) {
        this.developerEmail = developerEmail;
    }

    public String getDeveloperInstitution() {
        return developerInstitution;
    }

    public void setDeveloperInstitution(String developerInstitution) {
        this.developerInstitution = developerInstitution;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
