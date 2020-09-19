package com.company.storeapi.core.exceptions.base;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecurityException extends RuntimeException {

    private final LogRefServices logRefServices;
    private final String message;
    private String status;

    public SecurityException(LogRefServices logRefServices, String message, String status) {
        this.logRefServices = logRefServices;
        this.message = message;
        this.status = status;
    }

    public SecurityException(LogRefServices logRefServices, String message, Throwable cause, String status){
        super(message,cause);
        this.logRefServices= logRefServices;
        this.message= message;
        this.status = status;

    }


    public SecurityException(LogRefServices logRefServices, String message) {
        this.logRefServices= logRefServices;
        this.message= message;
    }
}
