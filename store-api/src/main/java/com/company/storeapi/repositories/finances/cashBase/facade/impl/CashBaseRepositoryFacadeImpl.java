package com.company.storeapi.repositories.finances.cashBase.facade.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.finance.CashBase;
import com.company.storeapi.repositories.finances.cashBase.CashBaseRepository;
import com.company.storeapi.repositories.finances.cashBase.facade.CashBaseRepositoryFacade;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CashBaseRepositoryFacadeImpl implements CashBaseRepositoryFacade {

    private final CashBaseRepository cashBaseRepository;

    public CashBaseRepositoryFacadeImpl(CashBaseRepository cashBaseRepository) {
        this.cashBaseRepository = cashBaseRepository;
    }

    @Override
    public CashBase findCashBaseByUltime() {
        return Optional.ofNullable(cashBaseRepository.findCashBaseByCashRegister(false))
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No existe registro de la base diaria "));
    }

    @Override
    public CashBase save(CashBase cashBase) {
        return cashBaseRepository.save(cashBase);
    }

    @Override
    public boolean existsByCashRegister(boolean cash) {
        return cashBaseRepository.existsByCashRegister(cash);
    }
}
