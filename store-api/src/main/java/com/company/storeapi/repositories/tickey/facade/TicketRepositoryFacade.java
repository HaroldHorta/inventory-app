package com.company.storeapi.repositories.tickey.facade;

import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;

import java.util.List;

public interface TicketRepositoryFacade {

    List<Ticket> getAllTicket();

    List<Ticket> getAllTicketByCashRegister();

    Ticket validateAndGetTicketById (String id);

    Ticket saveTicket(Ticket ticket);

    Ticket findTicketByOrder(String id);

    List<Ticket> findTicketByCustomer_NroDocument (String nroDocument);

    boolean existsByCashRegister(boolean cash);
}
