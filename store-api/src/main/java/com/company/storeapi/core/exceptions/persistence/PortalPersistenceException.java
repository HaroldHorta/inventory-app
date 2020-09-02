package com.company.storeapi.core.exceptions.persistence;

import com.company.storeapi.core.exceptions.base.PortalException;
import com.company.storeapi.core.exceptions.enums.LogRefServices;

/**
 * The type Portal persistence exception.
 */
public class PortalPersistenceException extends PortalException {

    /**
     * Instantiates a new Portal persistence exception.
     *
     * @param logRefServices the log ref services
     * @param message        the message
     */
    public PortalPersistenceException(LogRefServices logRefServices, String message){
        super(logRefServices, message);
    }

    /**
     * Instantiates a new Portal persistence exception.
     *
     * @param logRefServices the log ref services
     * @param message        the message
     * @param cause          the cause
     */
    public PortalPersistenceException(LogRefServices logRefServices, String message, Throwable cause){
        super(logRefServices, message, cause);
    }
}
