package com.company.storeapi.model.payload.response.finance;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ResponseCashRegisterDTO {

    private String description;
    private Double dailyCashBase;
    private Double dailyCashSales;
    private Double dailyTransactionsSales;
    private Double dailyCreditSales;
    private Double totalSales;
    private Double moneyOut;
    private Double discounts;
    private Double moneyMissing;
    private Double remainingMoney;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
}
