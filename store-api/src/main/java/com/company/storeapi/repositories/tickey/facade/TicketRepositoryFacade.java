package com.company.storeapi.repositories.tickey.facade;

import com.company.storeapi.model.entity.Ticket;

import java.util.List;

public interface TicketRepositoryFacade {

    List<Ticket> getAllTicket();

    List<Ticket> getAllTicketByCashRegister();

    Ticket validateAndGetTicketById (String id);

    Ticket saveTicket(Ticket ticket);

    List<Ticket> findTicketByCustomer_NroDocument (String nroDocument);

}
