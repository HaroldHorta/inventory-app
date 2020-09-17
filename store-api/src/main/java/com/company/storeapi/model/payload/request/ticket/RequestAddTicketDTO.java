package com.company.storeapi.model.payload.request.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RequestAddTicketDTO {

    @Schema(example = "5f4eee53b88119177021b61b")
    private String order;
}
