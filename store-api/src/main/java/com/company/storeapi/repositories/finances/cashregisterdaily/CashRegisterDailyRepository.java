package com.company.storeapi.repositories.finances.cashregisterdaily;

import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CashRegisterDailyRepository extends MongoRepository<CashRegisterDaily, String> {

    CashRegisterDaily findAllByCashRegister(boolean cashRegister);

    boolean existsCashRegisterDailiesByCashRegister(boolean cash);

    List<CashRegisterDaily> findAllByPageable (boolean pag, Pageable pageable);

    int countByPageable (boolean pag);

}
