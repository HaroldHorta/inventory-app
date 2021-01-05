package com.company.storeapi.repositories.finances.cashRegisterDaily;

import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CashRegisterDailyRepository extends MongoRepository<CashRegisterDaily, String> {

    @Query("{cashRegister:?0}")
    CashRegisterDaily findCashRegisterDailyByCashRegister(boolean cashRegister);

    boolean existsCashRegisterDailiesByCashRegister(boolean cash);

    List<CashRegisterDaily> findAllByPageable (boolean pag, Pageable pageable);
}
