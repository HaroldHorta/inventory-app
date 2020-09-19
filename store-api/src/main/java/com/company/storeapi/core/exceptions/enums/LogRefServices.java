package com.company.storeapi.core.exceptions.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Log ref servicios.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LogRefServices {

    /**
     * Error general servicio log ref servicios.
     */
    LOG_REF_SERVICES("LOG_REF_SERVICES", "/ayuda/error_general_servicio.html"),
    /**
     * Error persistencia log ref servicios.
     */
    ERROR_PERSISTENCE("ERROR_PERSISTENCE", "/ayuda/error_persistencia.html"),
    /**
     * Error dato corrupto log ref servicios.
     */
    ERROR_DATA_CORRUPT("ERROR_DATA_CORRUPT", "/ayuda/error_dato_corrupto.html"),
    ERROR_DATA_NOT_FOUND("ERROR_DATA_NOT_FOUND", "/ayuda/error_general_servicio.html"),
    /**
     * Error conversion fecha log ref servicios.
     */
    ERROR_CONVERSION_DATE("ERROR_CONVERSION_DATE", "/ayuda/error_general/error_conversion_fecha.html"),
    /**
     * Error llave parametro no encontrado log ref servicios.
     */
    ERROR_SAVE("ERROR_SAVE", "/ayuda/error_general/error_guardar_solicitud.html"),

    ERROR_AUTHENTICATION("ERROR_AUTHENTICATION","/ayuda/error_general/error_athentication.html"),

    ERROR_TOKEN_INVALID("ERROR_TOKEN_INVALID","/ayuda/error_general/error_athentication.html"),

    ERROR_NO_SUPPORTED("ERROR_NO_SUPPORTED","/ayuda/error_general/error_athentication.html"),

    ERROR_JWT_EMPTY("ERROR_JWT_EMPTY","/ayuda/error_general/error_athentication.html"),

    ERROR_EXPIRED("ERROR_EXPIRED","/ayuda/error_general/error_athentication.html");


    /**
     * Codigo del error
     */
    private @Getter
    final
    String logRef;
    /**
     * Enlace a la pagina con ayuda
     */
    private @Getter
    final
    String hrefLink;

}

