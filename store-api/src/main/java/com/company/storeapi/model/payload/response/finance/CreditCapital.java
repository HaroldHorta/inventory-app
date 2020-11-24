package com.company.storeapi.model.payload.response.finance;

import com.company.storeapi.model.enums.PaymentType;

import java.util.Date;


public class CreditCapital {

    private double cashCreditCapital;
    private double transactionCreditCapital;
    private Date creatAt;
    private boolean cashRegister;
    private PaymentType paymentType;

    public double getCashCreditCapital() {
        return cashCreditCapital;
    }

    public void setCashCreditCapital(double cashCreditCapital) {
        this.cashCreditCapital = cashCreditCapital;
    }

    public void setTransactionCreditCapital(double transactionCreditCapital) {
        this.transactionCreditCapital = transactionCreditCapital;
    }

    public double getTransactionCreditCapital() {
        return transactionCreditCapital;
    }


    public Date getCreatAt(Date date) {
        return creatAt;
    }

    public void setCreatAt(Date creatAt) {
        this.creatAt = creatAt;
    }

    public Date getCreatAt() {
        return creatAt;
    }

    public boolean isCashRegister() {
        return cashRegister;
    }

    public void setCashRegister(boolean cashRegister) {
        this.cashRegister = cashRegister;
    }

    public CreditCapital() {
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
