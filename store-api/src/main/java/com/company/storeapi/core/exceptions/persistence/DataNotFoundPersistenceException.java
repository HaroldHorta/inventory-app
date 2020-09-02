package com.company.storeapi.core.exceptions.persistence;

import com.company.storeapi.core.exceptions.enums.LogRefServices;

/**
 * The type Data not found persistence exception.
 */
public class DataNotFoundPersistenceException extends PortalPersistenceException {

    /**
     * Instantiates a new Data not found persistence exception.
     *
     * @param logRefServices the log ref services
     * @param message        the message
     */
    public DataNotFoundPersistenceException(LogRefServices logRefServices, String message){
        super(logRefServices, message);
    }

    /**
     * Instantiates a new Data not found persistence exception.
     *
     * @param logRefServices the log ref services
     * @param message        the message
     * @param cause          the cause
     */
    public DataNotFoundPersistenceException(LogRefServices logRefServices, String message, Throwable cause){
        super(logRefServices, message, cause);
    }
}
