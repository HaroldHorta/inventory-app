package com.company.storeapi.model.payload.response.ticket;

import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.enums.TicketStatus;
import lombok.Data;

@Data
public class ResponseTicketDTO {

    private String id;
    private String customer;
    private Order order;
    private String createAt;
    private PaymentType paymentType;
    private TicketStatus ticketStatus;
    private Double ticketCost;
    private Double ticketCostWithoutIVA;
    private Double outstandingBalance;
    private Double creditCapital;
}
