package com.company.storeapi.core.exceptions.service;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.core.exceptions.enums.LogRefServices;

/**
 * The type Data not found service exception.
 */
public class DataNotFoundServiceException extends ServiceException {

    /**
     * Instantiates a new Data not found service exception.
     *
     * @param logRefServices the log ref services
     * @param message        the message
     */
    public DataNotFoundServiceException(LogRefServices logRefServices, String message) {
        super(logRefServices, message);
    }

    /**
     * Instantiates a new Data not found service exception.
     *
     * @param logRefServices the log ref services
     * @param message        the message
     * @param cause          the cause
     */
    public DataNotFoundServiceException(LogRefServices logRefServices, String message, Throwable cause) {
        super(logRefServices, message, cause);
    }
}
