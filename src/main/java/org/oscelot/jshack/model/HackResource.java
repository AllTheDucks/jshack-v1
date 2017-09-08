/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.model;

/**
 *
 * @author Wiley Fuller <wiley@alltheducks.com>
 */
public class HackResource {
    private String path;
    private String mime;
    private boolean embed;

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the mime
     */
    public String getMime() {
        return mime;
    }

    /**
     * @param mime the mime to set
     */
    public void setMime(String mime) {
        this.mime = mime;
    }

    /**
     * @return the embed
     */
    public boolean isEmbed() {
        return embed;
    }

    /**
     * @param embed the embed to set
     */
    public void setEmbed(boolean embed) {
        this.embed = embed;
    }
}
