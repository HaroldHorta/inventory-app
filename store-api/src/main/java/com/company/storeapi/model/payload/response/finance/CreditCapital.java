package com.company.storeapi.model.payload.response.finance;

import com.company.storeapi.model.enums.PaymentType;

import java.util.Date;


public class CreditCapital {

    private Double CashCreditCapital;
    private Double TransactionCreditCapital;
    private Date creatAt;
    private boolean cashRegister;
    private PaymentType paymentType;

    public Double getCashCreditCapital() {
        return CashCreditCapital;
    }

    public void setCashCreditCapital(Double cashCreditCapital) {
        CashCreditCapital = cashCreditCapital;
    }

    public Double getTransactionCreditCapital() {
        return TransactionCreditCapital;
    }

    public void setTransactionCreditCapital(Double transactionCreditCapital) {
        TransactionCreditCapital = transactionCreditCapital;
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
