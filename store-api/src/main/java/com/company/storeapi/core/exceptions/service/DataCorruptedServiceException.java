package com.company.storeapi.core.exceptions.service;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.core.exceptions.enums.LogRefServices;

/**
 * The type Data corrupted service exception.
 */
public class DataCorruptedServiceException extends ServiceException {
    /**
     * Instantiates a new Data corrupted service exception.
     *
     * @param logRefServices the log ref services
     * @param message        the message
     */
    public DataCorruptedServiceException(LogRefServices logRefServices, String message) {
        super(logRefServices, message);
    }

    /**
     * Instantiates a new Data corrupted service exception.
     *
     * @param logRefServices the log ref services
     * @param message        the message
     * @param cause          the cause
     */
    public DataCorruptedServiceException(LogRefServices logRefServices, String message, Throwable cause) {
        super(logRefServices, message, cause);
    }
}
