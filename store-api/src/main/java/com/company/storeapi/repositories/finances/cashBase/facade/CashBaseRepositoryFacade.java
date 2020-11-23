package com.company.storeapi.repositories.finances.cashBase.facade;

import com.company.storeapi.model.entity.finance.CashBase;

import java.util.List;


public interface CashBaseRepositoryFacade {

    CashBase findCashBaseByUltime();

    CashBase save(CashBase cashBase);

    boolean existsByCashRegister(boolean cash);

}
