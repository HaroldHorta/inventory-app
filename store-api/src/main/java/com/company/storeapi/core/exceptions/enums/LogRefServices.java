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
    ERROR_GENERAL_SERVICIO("ERROR_GENERAL_SERVICIO", "/ayuda/error_general_servicio.html"),
    /**
     * Error persistencia log ref servicios.
     */
    ERROR_PERSISTENCIA("ERROR_PERSISTENCIA", "/ayuda/error_persistencia.html"),
    /**
     * Error dato corrupto log ref servicios.
     */
    ERROR_DATO_CORRUPTO("ERROR_DATO_CORRUPTO", "/ayuda/error_dato_corrupto.html"),
    ERROR_DATO_NO_ENCONTRADO("ERROR_DATO_NO_ENCONTRADO", "/ayuda/error_general_servicio.html"),
    /**
     * Error conversion fecha log ref servicios.
     */
    ERROR_CONVERSION_FECHA("ERROR_CONVERSION_FECHA", "/ayuda/error_general/error_conversion_fecha.html"),
    /**
     * Error llave parametro no encontrado log ref servicios.
     */
    ERROR_GUARDAR_SOLICITUD("ERROR_GUARDAR_SOLICITUD", "/ayuda/error_general/error_guardar_solicitud.html");

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

