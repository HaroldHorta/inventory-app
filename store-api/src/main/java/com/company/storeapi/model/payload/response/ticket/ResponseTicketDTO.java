package com.company.storeapi.model.payload.response.ticket;

import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.enums.TicketStatus;
import com.company.storeapi.model.payload.response.finance.CreditCapital;
import lombok.Data;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class ResponseTicketDTO {

    private String id;
    private Order order;
    private Customer customer;
    private Date createAt;
    private PaymentType paymentType;
    private TicketStatus ticketStatus;
    private Set<CreditCapital> creditCapitals = new LinkedHashSet<>();
    private double ticketCost;
    private double ticketCostWithoutIVA;
    private double outstandingBalance;
    private double cashPayment;
    private double transactionPayment;
    private double creditPayment;
    private boolean cashRegister;
}
