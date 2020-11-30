package com.company.storeapi.model.entity;

import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.enums.TicketStatus;
import com.company.storeapi.model.payload.response.finance.CreditCapital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    private String id;
    private Order order;
    private Customer customer;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
    private PaymentType paymentType;
    private TicketStatus ticketStatus;
    Set<CreditCapital> creditCapitals = new LinkedHashSet<>();
    private double ticketCost;
    private double ticketCostWithoutIVA;
    private double outstandingBalance;
    private double cashPayment;
    private double transactionPayment;
    private double creditPayment;
    private boolean cashRegister;
    private boolean cashRegisterCredit;

}
