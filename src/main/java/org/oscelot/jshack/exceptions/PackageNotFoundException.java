/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscelot.jshack.exceptions;

/**
 *
 * @author sargo
 */
public class PackageNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>PackageNotFoundException</code> without detail message.
     */
    public PackageNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>PackageNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PackageNotFoundException(String msg) {
        super(msg);
    }
}
