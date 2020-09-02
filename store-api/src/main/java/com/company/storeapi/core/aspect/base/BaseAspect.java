package com.company.storeapi.core.aspect.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The type Base aspect.
 */
@Slf4j
@Component
public class BaseAspect {

    private final ObjectMapper mapper;

    /**
     * Instantiates a new Base aspect.
     *
     * @param mapper the mapper
     */
    public BaseAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    /**
     * Gets payload.
     *
     * @param args the args
     * @return the payload
     */
    protected String getPayload(Object[] args) {
        String payload = null;
        try {
            payload = mapper.writeValueAsString(args);
        } catch (Exception e) {
            log.error("No se ha podido procesar la petición como un JSON - ERROR: {0} ", e);
        }
        return defaultIfNull(payload, "Entidad inprocesable.");
    }

}
