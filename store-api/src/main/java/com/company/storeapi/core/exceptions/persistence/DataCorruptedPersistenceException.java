package com.company.storeapi.core.exceptions.persistence;


import com.company.storeapi.core.exceptions.enums.LogRefServices;

public class DataCorruptedPersistenceException extends PortalPersistenceException{

    public DataCorruptedPersistenceException(LogRefServices logRefServices, String message){
        super(logRefServices, message);
    }

    public DataCorruptedPersistenceException(LogRefServices logRefServices, String message, Throwable cause){
        super(logRefServices, message,cause);
    }
}
