package com.company.storeapi.repositories.finances.cashregisterdaily.facade;

import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CashRegisterDailyRepositoryFacade {

    List<CashRegisterDaily> findAllCashRegisterDaily();

    List<CashRegisterDaily> findAllByPageable (boolean pag, Pageable pageable);

    CashRegisterDaily findCashRegisterDailyByUltimate();

    CashRegisterDaily save(CashRegisterDaily cashRegisterDaily);

    boolean existsCashRegisterDailiesByCashRegister(boolean cash);

    int countByPageable (boolean pag);


}
