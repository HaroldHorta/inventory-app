package com.company.storeapi.services.order;

import com.company.storeapi.model.dto.request.order.RequestAddOrderDTO;
import com.company.storeapi.model.dto.request.order.RequestUpdateOrderDTO;
import com.company.storeapi.model.dto.response.order.ResponseOrderDTO;

import java.util.List;

public interface OrderService {

    List<ResponseOrderDTO> getAllOrders();

    ResponseOrderDTO saveOrder(RequestAddOrderDTO order);

    ResponseOrderDTO updateOrder(String id, RequestUpdateOrderDTO requestUpdateCustomerDTO);

    ResponseOrderDTO validateAndGetOrderById(String id);

}
