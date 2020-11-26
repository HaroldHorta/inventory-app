package com.company.storeapi.model.payload.response.finance;

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
public class CreditCapital {

    private double cashCreditCapital;
    private double transactionCreditCapital;
    private Date creatAt;
    private PaymentType paymentType;

}
