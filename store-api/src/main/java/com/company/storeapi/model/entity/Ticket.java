package com.company.storeapi.model.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ticket")
public class Ticket {

    @Id
    private String id;
    @NonNull
    private Order order;
    private String createAt;

    public Ticket() {

    }
}
