package com.company.storeapi.services.order;

import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.payload.request.order.RequestAddOrderDTO;
import com.company.storeapi.model.payload.request.order.RequestUpdateOrderDTO;
import com.company.storeapi.model.payload.response.order.ResponseOrderDTO;

import java.util.List;

public interface OrderService {

    List<ResponseOrderDTO> getAllOrders();

    ResponseOrderDTO saveOrder(RequestAddOrderDTO order);

    ResponseOrderDTO updateOrder(String id, RequestUpdateOrderDTO requestUpdateCustomerDTO);

    ResponseOrderDTO validateAndGetOrderById(String id);

    ResponseOrderDTO changeStatusOrder(String id, OrderStatus orderStatus);

}
