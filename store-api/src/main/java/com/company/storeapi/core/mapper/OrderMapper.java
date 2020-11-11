package com.company.storeapi.core.mapper;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.model.entity.CountingGeneral;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.payload.request.order.RequestAddOrderDTO;
import com.company.storeapi.model.payload.request.order.RequestUpdateOrderDTO;
import com.company.storeapi.model.payload.request.product.RequestOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.order.ResponseOrderDTO;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.services.countingGeneral.CountingGeneralService;
import com.company.storeapi.services.customer.CustomerService;
import com.company.storeapi.services.order.OrderService;
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
    private ProductService productService;

    @Autowired OrderService orderService;

    @Autowired
    private CountingGeneralService countingGeneralService;

    @Autowired
    private ProductMapper productMapper;

    @Mapping(source = "products", target = "products")
    public abstract ResponseOrderDTO toOrderDto(Order order);

    public abstract RequestAddOrderDTO toRequestAdd (RequestUpdateOrderDTO requestUpdateOrderDTO);

    public abstract Set<ResponseOrderProductItemsDTO> responseOrderProductItemsDTO(List<RequestOrderProductItemsDTO> order);

    public void updateOrderFromDto(RequestUpdateOrderDTO updateOrderDto, Order order){

        Set<ResponseOrderProductItemsDTO> listOrder = getResponseOrderProductItemsDTOS(toRequestAdd(updateOrderDto));

        order.setProducts(listOrder);
    }

    public Order toOrder(RequestAddOrderDTO createOrderDto) {

        Order order = new Order();
        order.setId(createOrderDto.getId());
        order.setOrderStatus(OrderStatus.OPEN);

        Set<ResponseOrderProductItemsDTO> listOrder = getResponseOrderProductItemsDTOS(createOrderDto);

        order.setProducts(listOrder);

        List<CountingGeneral> counting = countingGeneralService.getAllCountingGeneral();

        if((counting.size() ==0)){
            CountingGeneral c = new CountingGeneral();

            c.setQuantity_of_orders_in_open_state(1);
            countingGeneralService.saveCountingGeneral(c);

        }  else{
            counting.forEach(p->{
                        CountingGeneral countingGeneral = countingGeneralService.validateCountingGeneral(p.getId());

                        countingGeneral.setQuantity_of_orders_in_open_state(p.getQuantity_of_orders_in_open_state()+1);

                    countingGeneralService.saveCountingGeneral(countingGeneral);
        });
        }


        return order;
    }

    private Set<ResponseOrderProductItemsDTO> getResponseOrderProductItemsDTOS(RequestAddOrderDTO createOrderDto) {
        Set<ResponseOrderProductItemsDTO> listOrder = new LinkedHashSet<>();
        createOrderDto.getProducts().forEach(p -> {
            Product product = productMapper.toProductResponse(productService.validateAndGetProductById(p.getProduct().getId()));
            if(product.getUnit()==0 || product.getStatus() == Status.INACTIVE){
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"Producto " + product.getName() + " Agotado o Inactivo");
            }else if(product.getUnit()<p.getUnit()){
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"La cantidad requerida del producto " + product.getName() + " es mayor a la cantidad existente en el inventario");
           } else if(p.getUnit()<=0){
               throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"La cantidad solicitada del producto " + product.getName() + " no puede ser 0 o menor");
           }else{
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
