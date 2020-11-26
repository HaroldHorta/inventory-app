package com.company.storeapi.repositories.finances.cashRegisterDaily;

import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CashRegisterDailyRepository extends MongoRepository<CashRegisterDaily, String> {

    @Query("{cashRegister:?0}")
    CashRegisterDaily findCashRegisterDailyByCashRegister(boolean cashRegister);

    boolean existsCashRegisterDailiesByCashRegister(boolean cash);
}
