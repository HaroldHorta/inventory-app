package com.company.storeapi.core.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * The type Refis message error.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageError {

    /**
     * The constant ERROR_EN_EL_ADMINISTRADOR_DE_PERSISTENCIA.
     */
    public static final String ERROR_EN_EL_ADMINISTRADOR_DE_PERSISTENCIA = "Error en el administrador de persistencia.";
    /**
     * The constant ERROR_EN_EL_ACCESO_AL_ADMINISTRADOR_DE_PERSISTENCIA.
     */
    public static final String ERROR_EN_EL_ACCESO_AL_ADMINISTRADOR_DE_PERSISTENCIA = "Error en el acceso al administrador de persistencia.";
    /**
     * The constant ERROR_EN_EL_ACCESO_LA_ENTIDAD.
     */
    public static final String ERROR_EN_EL_ACCESO_LA_ENTIDAD = "Error en el acceso la entidad.";
    /**
     * The constant ERROR_PERSISTENCIA_EN_CASCADA.
     */
    public static final String ERROR_PERSISTENCIA_EN_CASCADA = "Error en la persistencia en casacada. Al parecer esta referenciando una entidad o relación inexistente.";
    /**
     * The constant NO_SE_HA_ENCONTRADO_LA_ENTIDAD.
     */
    public static final String NO_SE_HA_ENCONTRADO_LA_ENTIDAD = "No se ha encontrado la entidad.";
    /**
     * The constant ERROR_AL_ELIMINAR_LA_ENTIDAD.
     */
    public static final String ERROR_AL_ELIMINAR_LA_ENTIDAD = "Error al eliminar la entidad.";
    /**
     * The constant ERROR_AL_PERSISTIR_LA_LISTA_DE_ENTIDADADES.
     */
    public static final String ERROR_AL_PERSISTIR_LA_LISTA_DE_ENTIDADADES = "Error al persistir la lista de entidadades.";
    /**
     * The constant ERROR_SERVICE_CONSUME_HAS_FAILED_MESSAGE_BECAUSE_OF.
     */
    public static final String ERROR_SERVICE_CONSUME_HAS_FAILED_MESSAGE_BECAUSE_OF = "ERROR: Service consume has failed. Message {} because of: {}";
    /**
     * The constant ERROR_EN_EL_CONSUMO_DEL_CLIENTE.
     */
    public static final String ERROR_EN_EL_CONSUMO_DEL_CLIENTE = "Error en el consumo del cliente";

    /**
     * The constant NO_SE_HA_ENCONTRADO_LA_LISTA_PARAMETRICA.
     */
    public static final String NO_SE_HA_ENCONTRADO_LA_LISTA_PARAMETRICA = "No se ha encontrado la lista parametrica.";
}
