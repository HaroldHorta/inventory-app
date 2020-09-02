package com.company.storeapi.repositories.tickey.facade;

import com.company.storeapi.model.entity.Ticket;

import java.util.List;

public interface TicketRepositoryFacade {

    List<Ticket> getAllTicket();

    Ticket validateAndGetTicketById (String id);

    Ticket saveTicket(Ticket ticket);

}
