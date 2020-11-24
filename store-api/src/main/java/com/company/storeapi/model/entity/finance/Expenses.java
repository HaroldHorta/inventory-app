package com.company.storeapi.model.entity.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "expenses")
public class Expenses {

    @Id
    private String id;
    private String description;
    private double price;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;

}
