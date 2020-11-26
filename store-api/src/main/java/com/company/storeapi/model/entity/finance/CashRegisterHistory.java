package com.company.storeapi.model.entity.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cashRegisterHistory")
public class CashRegisterHistory {

    @Id
    private String id;
    private double dailyCashBase;
    private double dailyCashSales;
    private double dailyTransactionsSales;
    private double dailyCreditSales;
    private double totalSales;
    private double moneyOut;
    //private double discounts;
    private double cashCreditCapital;
    private double transactionCreditCapital;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;

}
