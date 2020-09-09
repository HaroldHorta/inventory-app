package com.company.storeapi.core.mapper;

import com.company.storeapi.core.util.DateUtil;
import com.company.storeapi.model.dto.request.ticket.RequestAddTicketDTO;
import com.company.storeapi.model.dto.response.order.ResponseOrderDTO;
import com.company.storeapi.model.dto.response.ticket.ResponseTicketDTO;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.services.order.OrderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {OrderService.class}
)
public abstract class TicketMapper {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Mapping(source = "order",target = "order")
    public abstract ResponseTicketDTO toTicketDto(Ticket ticket);

    public Ticket toTicket (RequestAddTicketDTO requestAddTicketDTO){
        Ticket ticket = new Ticket();
        ResponseOrderDTO order = orderService.validateAndGetOrderById(requestAddTicketDTO.getOrder());
        ticket.setCreateAt(DateUtil.getDateActual());
        ticket.setOrder(orderMapper.toResponseDto(order));

        return ticket;
    }
}
