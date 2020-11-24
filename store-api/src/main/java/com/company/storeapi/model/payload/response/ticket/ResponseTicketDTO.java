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
    private double ticketCost;
    private double ticketCostWithoutIVA;
    private Set<CreditCapital> creditCapital = new LinkedHashSet<>();
    private double outstandingBalance;
    private double cashPayment;
    private double transactionPayment;
    private double creditPayment;
    private boolean cashRegister;
}
