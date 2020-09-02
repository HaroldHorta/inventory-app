package com.company.storeapi.repositories.tickey;

import com.company.storeapi.model.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {
}
