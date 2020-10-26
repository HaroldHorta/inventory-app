package com.company.storeapi.repositories.order.facade;

import com.company.storeapi.model.entity.Order;

import java.util.List;

public interface OrderRepositoryFacade {

    List<Order> getAllOrders();

    Order saveOrder(Order order);

    Order validateAndGetOrderById(String id);

    List<Order> findOrderByProducts(String id);
}
