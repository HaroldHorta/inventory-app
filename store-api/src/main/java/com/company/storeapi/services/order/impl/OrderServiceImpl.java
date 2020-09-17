package com.company.storeapi.services.order.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.OrderMapper;
import com.company.storeapi.model.payload.request.order.RequestAddOrderDTO;
import com.company.storeapi.model.payload.request.order.RequestUpdateOrderDTO;
import com.company.storeapi.model.payload.response.order.ResponseOrderDTO;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import com.company.storeapi.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepositoryFacade orderRepository;
    private final OrderMapper orderMapper;


    @Override
    public List<ResponseOrderDTO> getAllOrders() {
        return orderRepository.getAllOrders().stream().map(orderMapper::toOrderDto).collect(Collectors.toList());
    }

    @Override
    public ResponseOrderDTO saveOrder(RequestAddOrderDTO requestAddOrderDTO) {
        return orderMapper.toOrderDto(orderRepository.saveOrder(orderMapper.toOrder(requestAddOrderDTO)));
    }

     @Override
    public ResponseOrderDTO updateOrder(String id, RequestUpdateOrderDTO requestUpdateCustomerDTO) {
        Order order = orderRepository.validateAndGetOrderById(id);
        if (order.getOrderStatus().equals(OrderStatus.PAYED)) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_GUARDAR_SOLICITUD, "La orden ya esta cerrada, no se puede modificar");
        } else {
            order.setProducts(orderMapper.responseOrderProductItemsDTO(requestUpdateCustomerDTO.getProducts()));
            orderMapper.updateOrderFromDto(requestUpdateCustomerDTO, order);
            return orderMapper.toOrderDto(orderRepository.saveOrder(order));
        }
    }

    @Override
    public ResponseOrderDTO validateAndGetOrderById(String id) {
        return orderMapper.toOrderDto(orderRepository.validateAndGetOrderById(id));
    }


}
