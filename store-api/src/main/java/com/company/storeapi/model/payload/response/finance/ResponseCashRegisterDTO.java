package com.company.storeapi.model.payload.response.finance;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ResponseCashRegisterDTO {

    private double dailyCashBase;
    private double dailyCashSales;
    private double dailyTransactionsSales;
    private double dailyCreditSales;
    private double totalSales;
    private double moneyOut;
   // private double discounts;
   private Double cashCreditCapital;
    private Double transactionCreditCapital;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
}
