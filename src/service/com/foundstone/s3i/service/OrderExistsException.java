package com.foundstone.s3i.service;


/**
 * An exception that is thrown by classes wanting to trap unique 
 * constraint violations.  This is used to wrap Spring's 
 * DataIntegrityViolationException so it's checked in the web layer.
 *
 * <p><a href="UserExistsException.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class OrderExistsException extends Exception {

    /**
     * Constructor for UserExistsException.
     *
     * @param message
     */
    public OrderExistsException(String message) {
        super(message);
    }
}
