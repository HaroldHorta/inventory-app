package com.company.storeapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "countingGeneral")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountingGeneral {
    @Id
    private String id;
    private int quantity_of_orders_in_cancelled_state;
    private int quantity_of_orders_in_open_state;
    private int quantity_of_orders_in_pending_state;
    private int quantity_of_orders_in_payed_state;
    private int quantity_of_customer;
    private int quantity_of_product;
}
