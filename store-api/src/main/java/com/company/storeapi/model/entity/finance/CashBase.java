package com.company.storeapi.model.entity.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cashBase")
@Builder
public class CashBase {

    @Id
    private String id;
    private double dailyCashBase;
    private boolean cashRegister;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
}
