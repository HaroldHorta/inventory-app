package com.company.storeapi.model.entity.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cashRegister")
public class CashRegister {

    @Id
    private String id;
    private Double dailyCashBase;
    private Double dailyCashSales;
    private Double dailyTransactionsSales;
    private Double dailyCreditSales;
    private Double totalSales;
    private Double moneyOut;
    //private Double discounts;
    private Double cashCreditCapital;
    private Double transactionCreditCapital;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;

}
