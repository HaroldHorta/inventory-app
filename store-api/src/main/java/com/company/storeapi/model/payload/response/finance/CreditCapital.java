package com.company.storeapi.model.payload.response.finance;

import java.util.Date;


public class CreditCapital {

    private Double creditCapital;
    private Date creatAt;
    private boolean cashRegister;

    public Double getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(Double creditCapital) {
        this.creditCapital = creditCapital;
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
}
