package com.company.storeapi.model.entity.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cashRegisterDaily")
public class CashRegisterDaily {

    @Id
    private String id;
    private double dailyCashSales;
    private double dailyTransactionsSales;
    private double dailyCreditSales;
    private double cashCreditCapital;
    private double transactionCreditCapital;
    private boolean cashRegister;


}
