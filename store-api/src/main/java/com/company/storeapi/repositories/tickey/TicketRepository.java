package com.company.storeapi.repositories.tickey;

import com.company.storeapi.model.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {

    List<Ticket> findTicketByCustomer_NroDocument (String nroDocument);

}
