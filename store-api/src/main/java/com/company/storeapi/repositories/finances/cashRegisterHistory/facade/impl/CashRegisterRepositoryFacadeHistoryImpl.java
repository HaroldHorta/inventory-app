package com.company.storeapi.repositories.finances.cashRegisterHistory.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.finance.CashRegisterHistory;
import com.company.storeapi.repositories.finances.cashRegisterHistory.CashRegisterRepositoryHistory;
import com.company.storeapi.repositories.finances.cashRegisterHistory.facade.CashRegisterRepositoryFacadeHistory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CashRegisterRepositoryFacadeHistoryImpl implements CashRegisterRepositoryFacadeHistory {

    private final CashRegisterRepositoryHistory cashRegisterRepositoryHistory;

    public CashRegisterRepositoryFacadeHistoryImpl(CashRegisterRepositoryHistory cashRegisterRepositoryHistory) {
        this.cashRegisterRepositoryHistory = cashRegisterRepositoryHistory;
    }

    @Override
    public List<CashRegisterHistory> getCashRegister() {
        try {
            return Optional.of(cashRegisterRepositoryHistory.findAll())
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de cierre de caja"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public CashRegisterHistory saveCashRegister(CashRegisterHistory cashRegisterHistory) {
        return cashRegisterRepositoryHistory.save(cashRegisterHistory);
    }
}
