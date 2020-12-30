package com.company.storeapi.services.countingGeneral.impl;

import com.company.storeapi.model.entity.CountingGeneral;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.repositories.countingGeneral.facade.CountingGeneralRepositoryFacade;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import com.company.storeapi.services.countingGeneral.CountingGeneralService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountingGeneralServiceImpl implements CountingGeneralService {

    private final CountingGeneralRepositoryFacade countingGeneralRepositoryFacade;
    private final OrderRepositoryFacade orderRepository;

    public CountingGeneralServiceImpl(CountingGeneralRepositoryFacade countingGeneralRepositoryFacade, OrderRepositoryFacade orderRepository) {
        this.countingGeneralRepositoryFacade = countingGeneralRepositoryFacade;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<CountingGeneral> getAllCountingGeneral() {
        return countingGeneralRepositoryFacade.getAllCountingGeneral();
    }

    @Override
    public CountingGeneral saveCountingGeneral(CountingGeneral countingGeneral) {
        return countingGeneralRepositoryFacade.saveCountingGeneral(countingGeneral);
    }

    @Override
    public void counting (String idOrder, OrderStatus orderStatus){
        Order order = orderRepository.validateAndGetOrderById(idOrder);

        List<CountingGeneral> counting = countingGeneralRepositoryFacade.getAllCountingGeneral();

        counting.forEach(p->{
            CountingGeneral countingGeneral = countingGeneralRepositoryFacade.validateCountingGeneral(p.getId());

            if(order.getOrderStatus()== OrderStatus.ABIERTA){
                countingGeneral.setQuantity_of_orders_in_open_state(countingGeneral.getQuantity_of_orders_in_open_state()-1);
                if(orderStatus == OrderStatus.PAGADA){
                    countingGeneral.setQuantity_of_orders_in_payed_state(countingGeneral.getQuantity_of_orders_in_payed_state()+1);
                }if(orderStatus == OrderStatus.CANCELADA){
                    countingGeneral.setQuantity_of_orders_in_cancelled_state(countingGeneral.getQuantity_of_orders_in_cancelled_state()+1);
                }
            }

            if(order.getOrderStatus()== OrderStatus.CANCELADA){
                countingGeneral.setQuantity_of_orders_in_cancelled_state(countingGeneral.getQuantity_of_orders_in_cancelled_state()-1);
                if(orderStatus == OrderStatus.ABIERTA){
                    countingGeneral.setQuantity_of_orders_in_open_state(countingGeneral.getQuantity_of_orders_in_open_state()+1);
                }
            }

            countingGeneralRepositoryFacade.saveCountingGeneral(countingGeneral);
        });




    }

    @Override
    public CountingGeneral validateCountingGeneral(String id) {
        return countingGeneralRepositoryFacade.validateCountingGeneral(id);
    }
}
