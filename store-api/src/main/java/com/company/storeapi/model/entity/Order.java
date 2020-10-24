package com.company.storeapi.model.entity;

import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.enums.PaymentType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashSet;
import java.util.Set;

@Document(collection = "order")
@Data
@ToString()
@EqualsAndHashCode()

public class Order {

    @Id
    private String id;

    private Set<ResponseOrderProductItemsDTO> products = new LinkedHashSet<>();

    private PaymentType paymentType;

    private OrderStatus orderStatus;




}
