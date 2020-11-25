package com.company.storeapi.repositories.finances.creditCapital;

import com.company.storeapi.model.entity.CreditCapital;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CreditCapitalRepository extends MongoRepository<CreditCapital,String> {

    List<CreditCapital> findCreditCapitalByIdTicket(String idTicket);

}
