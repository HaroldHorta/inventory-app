package com.company.storeapi.model.entity;

import com.company.storeapi.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "creditCapital")
public class CreditCapital {

    @Id
    private String id;
    private String idTicket;
    private double cashCreditCapital;
    private double transactionCreditCapital;
    private Date creatAt;
    private boolean cashRegister;
    private PaymentType paymentType;

}
