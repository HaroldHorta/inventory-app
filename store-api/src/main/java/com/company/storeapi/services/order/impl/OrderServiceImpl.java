package com.company.storeapi.services.order.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.OrderMapper;
import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.model.entity.CountingGeneral;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.order.RequestAddOrderDTO;
import com.company.storeapi.model.payload.request.order.RequestUpdateOrderDTO;
import com.company.storeapi.model.payload.response.order.ResponseOrderDTO;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import com.company.storeapi.services.countingGeneral.CountingGeneralService;
import com.company.storeapi.services.order.OrderService;
import com.company.storeapi.services.product.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    private final ProductMapper productMapper;

    private final ProductService productService;

    private final OrderRepositoryFacade orderRepository;
    private final OrderMapper orderMapper;
    private final CountingGeneralService countingGeneralService;

    public OrderServiceImpl(ProductMapper productMapper, ProductService productService, @Lazy OrderRepositoryFacade orderRepository, OrderMapper orderMapper, CountingGeneralService countingGeneralService) {
        this.productMapper = productMapper;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.countingGeneralService = countingGeneralService;
    }


    @Override
    public List<ResponseOrderDTO> getAllOrders() {
        return orderRepository.getAllOrders().stream().map(orderMapper::toOrderDto).collect(Collectors.toList());
    }

    @Override
    public ResponseOrderDTO saveOrder(RequestAddOrderDTO requestAddOrderDTO) {


        Order order = new Order();
        order.setId(requestAddOrderDTO.getId());
        order.setOrderStatus(OrderStatus.ABIERTA);

        Set<ResponseOrderProductItemsDTO> listOrder = getResponseOrderProductItemsDTOS(requestAddOrderDTO);

        order.setProducts(listOrder);
        order.setTotalOrder(requestAddOrderDTO.getTotalOrder());

        List<CountingGeneral> counting = countingGeneralService.getAllCountingGeneral();

        if ((counting.isEmpty())) {
            CountingGeneral c = new CountingGeneral();

            c.setQuantity_of_orders_in_open_state(1);
            countingGeneralService.saveCountingGeneral(c);

        } else {
            counting.forEach(p -> {
                CountingGeneral countingGeneral = countingGeneralService.validateCountingGeneral(p.getId());

                countingGeneral.setQuantity_of_orders_in_open_state(p.getQuantity_of_orders_in_open_state() + 1);

                countingGeneralService.saveCountingGeneral(countingGeneral);
            });
        }

        return orderMapper.toOrderDto(orderRepository.saveOrder(order));
    }

    @Override
    public ResponseOrderDTO updateOrder(String id, RequestUpdateOrderDTO requestUpdateCustomerDTO) {
        Order order = orderRepository.validateAndGetOrderById(id);
        if (order.getOrderStatus().equals(OrderStatus.PAGADA)) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_SAVE, "La orden ya esta cerrada, no se puede modificar");
        } else {
            order.setProducts(orderMapper.responseOrderProductItemsDTO(requestUpdateCustomerDTO.getProducts()));

            Set<ResponseOrderProductItemsDTO> listOrder = getResponseOrderProductItemsDTOS(orderMapper.toRequestAdd(requestUpdateCustomerDTO));

            order.setProducts(listOrder);

            return orderMapper.toOrderDto(orderRepository.saveOrder(order));
        }
    }

    @Override
    public ResponseOrderDTO validateAndGetOrderById(String id) {
        return orderMapper.toOrderDto(orderRepository.validateAndGetOrderById(id));
    }

    @Override
    public ResponseOrderDTO changeStatusOrder(String id, OrderStatus orderStatus) {
        Order order = orderRepository.validateAndGetOrderById(id);
        order.setOrderStatus(orderStatus);
        countingGeneralService.counting(id, orderStatus);
        return orderMapper.toOrderDto(orderRepository.saveOrder(order));
    }

    private Set<ResponseOrderProductItemsDTO> getResponseOrderProductItemsDTOS(RequestAddOrderDTO createOrderDto) {
        Set<ResponseOrderProductItemsDTO> listOrder = new LinkedHashSet<>();
        createOrderDto.getProducts().forEach(p -> {

            ResponseProductDTO responseProductDTO = productService.validateAndGetProductById(p.getProduct().getId());

            Product product = new Product();
            product.setId(responseProductDTO.getId());
            product.setName(responseProductDTO.getName());
            product.setDescription(responseProductDTO.getDescription());

            product.setCategory(responseProductDTO.getCategory());
            product.setStatus(Status.ACTIVO);
            product.setCreateAt(new Date());
            product.setUpdateAt(new Date());
            product.setPriceBuy(responseProductDTO.getPriceBuy());
            product.setPriceSell(responseProductDTO.getPriceSell());
            product.setUnit(responseProductDTO.getUnit());

            if (product.getUnit() == 0 || product.getStatus() == Status.INACTIVO) {
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "Producto " + product.getName() + " Agotado o Inactivo");
            } else if (product.getUnit() < p.getUnit()) {
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La cantidad requerida del producto " + product.getName() + " es mayor a la cantidad existente en el inventario");
            } else if (p.getUnit() <= 0) {
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La cantidad solicitada del producto " + product.getName() + " no puede ser 0 o menor");
            } else {
                ResponseOrderProductItemsDTO requestOrderProductItemsDTO = new ResponseOrderProductItemsDTO();

                requestOrderProductItemsDTO.setProduct(productMapper.toProductDto(product));
                requestOrderProductItemsDTO.setUnit(p.getUnit());
                requestOrderProductItemsDTO.setTotal(product.getPriceSell() * p.getUnit());

                listOrder.add(requestOrderProductItemsDTO);
            }

        });
        return listOrder;
    }


}
