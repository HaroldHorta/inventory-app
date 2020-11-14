package com.company.storeapi.model.entity;

import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashSet;
import java.util.Set;

@Document(collection = "order")
@Data
@ToString()
@EqualsAndHashCode()

public class Order {

    @Indexed(unique=true, sparse=true)
    private String id;

    private Set<ResponseOrderProductItemsDTO> products = new LinkedHashSet<>();

    private OrderStatus orderStatus;

    private Double totalOrder;

}
