package com.company.storeapi.model.dto.response.ticket;

import com.company.storeapi.model.entity.Order;
import lombok.Data;

@Data
public class ResponseTicketDTO {

    private String id;
    private Order order;
    private String createAt;
}
