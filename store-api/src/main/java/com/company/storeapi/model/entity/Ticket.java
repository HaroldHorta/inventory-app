package com.company.storeapi.model.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "ticket")
public class Ticket {

    @Id
    private String id;
    @NonNull
    private Order order;
    private Customer customer;
    private Date createAt;

    public Ticket() {

    }
}
