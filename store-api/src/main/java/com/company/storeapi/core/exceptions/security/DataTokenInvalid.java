package com.company.storeapi.core.exceptions.security;

import com.company.storeapi.core.exceptions.base.SecurityException;
import com.company.storeapi.core.exceptions.enums.LogRefServices;

public class DataTokenInvalid extends SecurityException {

    public DataTokenInvalid(LogRefServices logRefServices, String message){
        super(logRefServices, message);
    }

    public DataTokenInvalid(LogRefServices logRefServices, String message, String cause){
        super(logRefServices, message,cause);
    }
}
