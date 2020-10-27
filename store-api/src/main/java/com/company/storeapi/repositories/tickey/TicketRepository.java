package com.company.storeapi.repositories.tickey;

import com.company.storeapi.model.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TicketRepository extends MongoRepository<Ticket, String> {

    @Query("{'order.id':?0}")
    Ticket findTicketByOrder(String id);
}
