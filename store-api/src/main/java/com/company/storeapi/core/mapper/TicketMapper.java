package com.company.storeapi.core.mapper;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.model.payload.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.payload.request.ticket.RequestAddTicketDTO;
import com.company.storeapi.model.payload.response.order.ResponseOrderDTO;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.services.order.OrderService;
import com.company.storeapi.services.product.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {OrderService.class, ProductService.class}
)
public abstract class TicketMapper {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @Mapping(source = "order",target = "order")
    public abstract ResponseTicketDTO toTicketDto(Ticket ticket);

    public Ticket toTicket (RequestAddTicketDTO requestAddTicketDTO){

        Ticket ticket = new Ticket();
        ResponseOrderDTO order = orderService.validateAndGetOrderById(requestAddTicketDTO.getOrder());

        order.setOrderStatus(OrderStatus.PAYED);
        orderService.updateOrder(order.getId(),orderMapper.toResponseAddDto(order));
        order.getProducts().forEach(p->{
                    Product product = productMapper.toProduct(productService.validateAndGetProductById(p.getId()));
                    if(product.getUnit()<=0){
                        throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATO_CORRUPTO,"Producto Agotado");
                    }else if(product.getUnit()>0){
                            int unitNew= product.getUnit()-p.getUnit();
                            if(unitNew<=0){
                                unitNew=0;
                                product.setStatus(Status.INACTIVE);
                            }
                        product.setUnit(unitNew);
                        RequestUpdateProductDTO productDTO = productMapper.toProductUpdate(product);
                        productDTO.setCategoryId(product.getCategory().getId());
                        productService.updateProduct(product.getId(), productDTO);
                    }
                }
        );

        ticket.setCreateAt(new Date());
        ticket.setOrder(orderMapper.toResponseDto(order));

        return ticket;
    }
}
