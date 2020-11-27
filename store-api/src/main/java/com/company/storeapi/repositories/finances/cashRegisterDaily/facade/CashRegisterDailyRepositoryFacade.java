package com.company.storeapi.repositories.finances.cashRegisterDaily.facade;

import com.company.storeapi.model.entity.finance.CashRegisterDaily;

public interface CashRegisterDailyRepositoryFacade {

    CashRegisterDaily findCashRegisterDailyByUltimate();

    CashRegisterDaily save(CashRegisterDaily cashRegisterDaily);

    boolean existsCashRegisterDailiesByCashRegister(boolean cash);
}
