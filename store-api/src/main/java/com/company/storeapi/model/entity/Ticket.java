package com.company.storeapi.model.entity;

import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.enums.TicketStatus;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "ticket")
public class Ticket {

    private String id;
    @NonNull
    private Order order;
    private Customer customer;
    private Date createAt;
    private PaymentType paymentType;
    private TicketStatus ticketStatus;
    private Double ticketCost;
    private Double ticketCostWithoutIVA;
    private Double outstandingBalance;
    private Double creditCapital;
    private boolean cashRegister;

    public Double getTicketCostWithoutIVA() {
        return ticketCostWithoutIVA;
    }

    public void setTicketCostWithoutIVA(Double ticketCostWithoutIVA) {
        this.ticketCostWithoutIVA = ticketCostWithoutIVA;
    }

    public Ticket() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Double getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(Double ticketCost) {
        this.ticketCost = ticketCost;
    }

    public Double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public Double getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(Double creditCapital) {
        this.creditCapital = creditCapital;
    }
}
