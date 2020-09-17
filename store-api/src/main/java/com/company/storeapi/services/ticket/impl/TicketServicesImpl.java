package com.company.storeapi.services.ticket.impl;

import com.company.storeapi.core.mapper.TicketMapper;
import com.company.storeapi.model.payload.request.ticket.RequestAddTicketDTO;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;
import com.company.storeapi.repositories.tickey.facade.TicketRepositoryFacade;
import com.company.storeapi.services.ticket.TicketServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServicesImpl implements TicketServices {

    private final TicketRepositoryFacade ticketRepositoryFacade;
    private final TicketMapper ticketMapper;

    @Override
    public List<ResponseTicketDTO> getAllTicket() {
        return ticketRepositoryFacade.getAllTicket().stream().map(ticketMapper::toTicketDto).collect(Collectors.toList());
    }

    @Override
    public ResponseTicketDTO validateAndGetTicketById(String id) {
        return ticketMapper.toTicketDto(ticketRepositoryFacade.validateAndGetTicketById(id));
    }

    @Override
    public ResponseTicketDTO saveTicket(RequestAddTicketDTO requestAddTicketDTO) {
        return ticketMapper.toTicketDto(ticketRepositoryFacade.saveTicket(ticketMapper.toTicket(requestAddTicketDTO)));
    }
}
