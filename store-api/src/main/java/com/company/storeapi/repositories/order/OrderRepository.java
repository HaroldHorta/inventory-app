package com.company.storeapi.repositories.order;

import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    @Query("{'products.product.id':?0}")
    List<Order> findOrderByProduct(String id);
}
