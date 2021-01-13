package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;
import com.company.storeapi.services.order.OrderService;
import com.company.storeapi.services.product.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {OrderService.class, ProductService.class}
)
public interface TicketMapper {

    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "order", target = "order")
    @Mapping(source = "creditCapitals", target = "creditCapitals")
    ResponseTicketDTO toTicketDto(Ticket ticket);

}
