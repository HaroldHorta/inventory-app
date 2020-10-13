package com.company.storeapi.model.entity;


import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.user.FileInfo;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Document(collection = "product")
@Data
public class Product {

    @Id
    private String id;

    @NotNull
    private String name;

    private String description;

    private Set<ResponseCategoryDTO> category = new LinkedHashSet<>();

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
