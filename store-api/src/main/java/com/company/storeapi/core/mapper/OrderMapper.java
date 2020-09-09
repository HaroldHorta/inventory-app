package com.company.storeapi.core.mapper;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.model.dto.request.order.RequestAddOrderDTO;
import com.company.storeapi.model.dto.request.order.RequestUpdateOrderDTO;
import com.company.storeapi.model.dto.request.product.RequestOrderProductItemsDTO;
import com.company.storeapi.model.dto.response.order.ResponseOrderDTO;
import com.company.storeapi.model.dto.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.services.customer.CustomerService;
import com.company.storeapi.services.product.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CustomerService.class, ProductService.class}
)

public abstract class OrderMapper {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ProductMapper productMapper;

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "products", target = "products")
    public abstract ResponseOrderDTO toOrderDto(Order order);

    public abstract Order toResponseDto(ResponseOrderDTO responseOrderDTO);

    public abstract RequestUpdateOrderDTO toResponseAddDto(ResponseOrderDTO responseOrderDTO);

    public abstract Set<ResponseOrderProductItemsDTO> responseOrderProductItemsDTO(List<RequestOrderProductItemsDTO> order);

    public abstract RequestAddOrderDTO toRequestAdd (RequestUpdateOrderDTO requestUpdateOrderDTO);

    public void updateOrderFromDto(RequestUpdateOrderDTO updateOrderDto, Order order){

        order.setPaymentType(updateOrderDto.getPaymentType());
        order.setOrderStatus(updateOrderDto.getOrderStatus());

        Set<ResponseOrderProductItemsDTO> listOrder = getResponseOrderProductItemsDTOS(toRequestAdd(updateOrderDto));

        order.setProducts(listOrder);
    }

    public Order toOrder(RequestAddOrderDTO createOrderDto) {
        Order order = new Order();
        order.setPaymentType(createOrderDto.getPaymentType());
        order.setOrderStatus(createOrderDto.getOrderStatus());

        Customer customer = customerMapper.toCustomer(customerService.validateAndGetCustomerById(createOrderDto.getCustomerId()));
        order.setCustomer(customer);

        Set<ResponseOrderProductItemsDTO> listOrder = getResponseOrderProductItemsDTOS(createOrderDto);

        order.setProducts(listOrder);

        return order;
    }

    private Set<ResponseOrderProductItemsDTO> getResponseOrderProductItemsDTOS(RequestAddOrderDTO createOrderDto) {
        Set<ResponseOrderProductItemsDTO> listOrder = new LinkedHashSet<>();
        createOrderDto.getProducts().forEach(p -> {
            Product product = productMapper.toProduct(productService.validateAndGetProductById(p.getId()));
            if(product.getUnit()==0){
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATO_CORRUPTO,"Producto " + product.getName() + " Agotado");
            }else if(product.getUnit()<p.getUnit()){
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATO_CORRUPTO,"La cantidad requerida del producto " + product.getName() + " es mayor a la cantidad existente en el inventario");
           } else if(p.getUnit()<=0){
               throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATO_CORRUPTO,"La cantidad solicitada del producto " + product.getName() + " no puede ser 0 o menor");
           }else{
               ResponseOrderProductItemsDTO requestOrderProductItemsDTO = new ResponseOrderProductItemsDTO();

               requestOrderProductItemsDTO.setId(product.getId());
               requestOrderProductItemsDTO.setUnit(p.getUnit());
               requestOrderProductItemsDTO.setTotal(product.getPriceSell() * p.getUnit());

               listOrder.add(requestOrderProductItemsDTO);
           }

        });
        return listOrder;
    }
}
