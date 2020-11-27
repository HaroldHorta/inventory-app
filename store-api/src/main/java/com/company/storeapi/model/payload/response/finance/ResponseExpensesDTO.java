package com.company.storeapi.model.payload.response.finance;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class ResponseExpensesDTO {

    private String id;
    private String description;
    private double price;
    private Set<String> images = new LinkedHashSet<>();
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;

}
