package com.company.storeapi.model.payload.response.finance;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class ResponseCashRegisterDTO {

    private double dailyCashBase;
    private AtomicReference<Double> dailyCashSales;
    private AtomicReference <Double> dailyTransactionsSales;
    private AtomicReference <Double> dailyCreditSales;
    private double totalSales;
    private double moneyOut;
    //private double discounts;
    private AtomicReference <Double> cashCreditCapital;
    private AtomicReference <Double> transactionCreditCapital;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
}
