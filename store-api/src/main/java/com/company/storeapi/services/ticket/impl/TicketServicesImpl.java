package com.company.storeapi.services.ticket.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.TicketMapper;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.enums.TicketStatus;
import com.company.storeapi.model.payload.request.ticket.RequestAddTicketDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
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

    @Override
    public List<ResponseTicketDTO> findTicketByCustomer_NroDocument(String nroDocument) {
        return ticketRepositoryFacade.findTicketByCustomer_NroDocument(nroDocument).stream().map(ticketMapper::toTicketDto).collect(Collectors.toList());
    }

    @Override
    public ResponseTicketDTO updateCreditCapital(String idTicket, Double creditCapital) {
        Ticket ticket = ticketRepositoryFacade.validateAndGetTicketById(idTicket);
        boolean validate = true;
        Double abono= ticket.getCreditCapital() + creditCapital;
        if(ticket.getTicketStatus() == TicketStatus.PAYED){
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La orden ha sido cancelada en su totalidad");
        }
        if (ticket.getTicketCost() > abono) {
            validate = false;
            Double credit = ticket.getOutstandingBalance() - creditCapital;
            ticket.setCreditCapital(abono);
            ticket.setOutstandingBalance(credit);
        }
        if (validate) {
            ticket.setOutstandingBalance((double) 0);
            ticket.setCreditCapital(ticket.getTicketCost());
            ticket.setTicketStatus(TicketStatus.PAYED);
        }
        return ticketMapper.toTicketDto(ticketRepositoryFacade.saveTicket(ticket));
    }
}
