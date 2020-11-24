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
    private double ticketCost;
    private double ticketCostWithoutIVA;
    private Set<CreditCapital> creditCapital = new LinkedHashSet<>();
    private double outstandingBalance;
    private double cashPayment;
    private double transactionPayment;
    private double creditPayment;
    private boolean cashRegister;

    public double getTicketCostWithoutIVA() {
        return ticketCostWithoutIVA;
    }

    public void setTicketCostWithoutIVA(double ticketCostWithoutIVA) {
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

    public double getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(double ticketCost) {
        this.ticketCost = ticketCost;
    }



    public boolean isCashRegister() {
        return cashRegister;
    }

    public void setCashRegister(boolean cashRegister) {
        this.cashRegister = cashRegister;
    }

    public double getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(double cashPayment) {
        this.cashPayment = cashPayment;
    }

    public double getTransactionPayment() {
        return transactionPayment;
    }

    public void setTransactionPayment(double transactionPayment) {
        this.transactionPayment = transactionPayment;
    }

    public double getCreditPayment() {
        return creditPayment;
    }

    public void setCreditPayment(double creditPayment) {
        this.creditPayment = creditPayment;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public Set<CreditCapital> getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(Set<CreditCapital> creditCapital) {
        this.creditCapital = creditCapital;
    }
}
