package com.company.storeapi.repositories.tickey;

import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.payload.response.finance.CreditCapital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {

    List<Ticket> findTicketByCustomer_NroDocument(String nroDocument);

    @Query(value = "{'creditCapital.cashRegister':?0}")
    List<Ticket> findTicketByCreditCapital(boolean cashRegister);


}
