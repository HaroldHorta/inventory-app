package com.company.storeapi.model.entity;


import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.user.FileInfo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "product")
@Data
public class Product {

    @Id
    private String id;

    @NotNull
    private String name;

    private String description;

    private Category category;

    private Status status;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date updateAt;

    private Double priceBuy;

    private Double priceSell;

    private int unit;

    private FileInfo photo;

    public Product() {

    }

   }
