package com.company.storeapi.repositories.finances.cashBase;

import com.company.storeapi.model.entity.finance.CashBase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface CashBaseRepository extends MongoRepository <CashBase, String>{

    @Query("{cashRegister:?0}")
    CashBase findCashBaseByCashRegister(boolean cashRegister);

    boolean existsByCashRegister(boolean cash);
}
