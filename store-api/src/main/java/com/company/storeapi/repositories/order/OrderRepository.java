package com.company.storeapi.repositories.order;

import com.company.storeapi.model.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

}
