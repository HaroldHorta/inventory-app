package com.company.storeapi.repositories.finances.cashRegisterDaily.facade.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.repositories.finances.cashRegisterDaily.CashRegisterDailyRepository;
import com.company.storeapi.repositories.finances.cashRegisterDaily.facade.CashRegisterDailyRepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CashRegisterDailyRepositoryFacadeImpl implements CashRegisterDailyRepositoryFacade {

    private final CashRegisterDailyRepository cashRegisterDailyRepository;
    @Override
    public CashRegisterDaily findCashRegisterDailyByUltimate() {
        return Optional.ofNullable(cashRegisterDailyRepository.findCashRegisterDailyByCashRegister(false))
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No existe registro de la base diaria "));

    }

    @Override
    public CashRegisterDaily save(CashRegisterDaily cashRegisterDaily) {
        return cashRegisterDailyRepository.save(cashRegisterDaily);
    }

    @Override
    public boolean existsCashRegisterDailiesByCashRegister(boolean cash) {
        return cashRegisterDailyRepository.existsCashRegisterDailiesByCashRegister(cash);
    }
}
