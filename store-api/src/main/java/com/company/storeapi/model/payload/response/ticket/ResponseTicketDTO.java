package com.company.storeapi.model.payload.response.ticket;

import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.enums.TicketStatus;
import com.company.storeapi.model.payload.response.finance.CreditCapital;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<CreditCapital> creditCapital = new LinkedHashSet<>();
    private Double outstandingBalance;
    private Double cashPayment;
    private Double transactionPayment;
    private Double creditPayment;
    private boolean cashRegister;
}
