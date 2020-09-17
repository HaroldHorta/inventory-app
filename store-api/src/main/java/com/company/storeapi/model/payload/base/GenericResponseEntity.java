package com.company.storeapi.model.payload.base;

import com.company.storeapi.core.exceptions.base.PortalException;
import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.model.payload.response.base.StatusDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericResponseEntity {

    private StatusDTO status;

    private LogRefServices errorType;

    private String detailMessage;

    private String traceRootException;

    @JsonIgnore
    private Exception rootException;

    public static GenericResponseEntity newBuilder(){
        return new GenericResponseEntity();
    }

    public GenericResponseEntity withStatus(HttpStatus httpStatus) {
        if (status == null) {
            this.status = new StatusDTO();
        }
        this.status.setCode(String.valueOf(httpStatus.value()));
        this.status.setDescription(httpStatus.name());
        return this;
    }

    public GenericResponseEntity withErrorType(Exception exception) {
        if (exception instanceof ServiceException) {
            this.errorType = ((ServiceException) exception).getLogRefServices();
        } else if (exception instanceof PortalException) {
            this.errorType = ((PortalException) exception).getLogRefServices();
        } else {
            this.errorType = LogRefServices.ERROR_GENERAL_SERVICIO;
        }
        return this;
    }

    public GenericResponseEntity withDetailMessage(String message) {
        this.detailMessage = message;
        return this;
    }

    public GenericResponseEntity withRootException(Exception exception) {
        Throwable trace = null;
        if (ObjectUtils.isNotEmpty(exception.getCause())) {
            trace = exception.getCause();
            if (ObjectUtils.isNotEmpty(trace.getCause())) {
                exception.getCause().getCause();
            }
        }
        this.rootException = exception;
        if (ObjectUtils.isNotEmpty(trace)) {
            this.traceRootException = StringUtils.isNotEmpty(trace.getMessage()) ? trace.getMessage()
                    .concat(" - ").concat(exception.getCause().toString())
                    : defaultIfNull(trace.getLocalizedMessage(), "No hay mensaje de excepción disponible.");
        }

        return this;
    }



    public GenericResponseEntity buildResponse() {
        return this;
    }


}
