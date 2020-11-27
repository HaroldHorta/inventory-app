package com.company.storeapi.repositories.finances.cashRegisterDaily.facade;

import com.company.storeapi.model.entity.finance.CashRegisterDaily;

import java.util.List;

public interface CashRegisterDailyRepositoryFacade {

    List<CashRegisterDaily> findAllCashRegisterDaily();

    CashRegisterDaily findCashRegisterDailyByUltimate();

    CashRegisterDaily save(CashRegisterDaily cashRegisterDaily);

    boolean existsCashRegisterDailiesByCashRegister(boolean cash);
}
