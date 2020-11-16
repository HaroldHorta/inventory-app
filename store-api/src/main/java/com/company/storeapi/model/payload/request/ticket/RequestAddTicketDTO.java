package com.company.storeapi.model.payload.request.ticket;

import com.company.storeapi.model.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestAddTicketDTO {

    private String id;
    @Schema(example = "5f4d93a00d70a908c47d3044")
    @NotNull
    private String customerId;

    @Schema(example = "5f4eee53b88119177021b61b")
    private String order;

    @Schema
    private PaymentType paymentType;

    @Schema(example = "0")
    private Double creditCapital;
}
