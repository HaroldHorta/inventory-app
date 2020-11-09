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
    private String description;
    private Double cashBase;
    private Double cashSales;
    private Double creditSales;
    private Double totalSales;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;

}
