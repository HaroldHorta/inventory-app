package com.company.storeapi.repositories.order.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.repositories.order.OrderRepository;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class OrderRepositoryFacadeImpl implements OrderRepositoryFacade {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        try {
            return Optional.of(orderRepository.findAll())
                        .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de ordenes"));
        }catch (IllegalArgumentException ie){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }


    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

     @Override
    public Order validateAndGetOrderById(String id) {
            return orderRepository.findById(id)
                    .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND,"No se encontraron ordenes con el " + id));
    }

    @Override
    public List<Order> findOrderByProducts(String id) {
        return Optional.of(orderRepository.findOrderByProduct(id))
                .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND,"No se encontraron ordenes registrados con ese producto" +id));

    }
}
