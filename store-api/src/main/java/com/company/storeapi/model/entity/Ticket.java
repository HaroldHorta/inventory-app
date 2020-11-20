package com.company.storeapi.model.entity;

import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.enums.TicketStatus;
import com.company.storeapi.model.payload.response.finance.CreditCapital;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Document(collection = "ticket")
public class Ticket {

    private String id;
    private Order order;
    private Customer customer;
    private Date createAt;
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



    public boolean isCashRegister() {
        return cashRegister;
    }

    public void setCashRegister(boolean cashRegister) {
        this.cashRegister = cashRegister;
    }

    public Double getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(Double cashPayment) {
        this.cashPayment = cashPayment;
    }

    public Double getTransactionPayment() {
        return transactionPayment;
    }

    public void setTransactionPayment(Double transactionPayment) {
        this.transactionPayment = transactionPayment;
    }

    public Double getCreditPayment() {
        return creditPayment;
    }

    public void setCreditPayment(Double creditPayment) {
        this.creditPayment = creditPayment;
    }

    public Double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public Set<CreditCapital> getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(Set<CreditCapital> creditCapital) {
        this.creditCapital = creditCapital;
    }
}
