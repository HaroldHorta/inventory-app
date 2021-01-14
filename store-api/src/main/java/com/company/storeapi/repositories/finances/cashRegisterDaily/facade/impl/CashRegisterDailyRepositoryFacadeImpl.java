package com.company.storeapi.repositories.finances.cashRegisterDaily.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.repositories.finances.cashRegisterDaily.CashRegisterDailyRepository;
import com.company.storeapi.repositories.finances.cashRegisterDaily.facade.CashRegisterDailyRepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CashRegisterDailyRepositoryFacadeImpl implements CashRegisterDailyRepositoryFacade {

    private final CashRegisterDailyRepository cashRegisterDailyRepository;

    @Override
    public List<CashRegisterDaily> findAllCashRegisterDaily() {
        try {
            return Optional.of(cashRegisterDailyRepository.findAll())
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se registros"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public List<CashRegisterDaily> findAllByPageable(boolean pag, Pageable pageable) {
        try {
            return cashRegisterDailyRepository.findAllByPageable(pag, pageable);
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public CashRegisterDaily findCashRegisterDailyByUltimate() {
        return Optional.ofNullable(cashRegisterDailyRepository.findCashRegisterDailyByCashRegister(false))
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se han registrado movimientos"));

    }

    @Override
    public CashRegisterDaily save(CashRegisterDaily cashRegisterDaily) {
        return cashRegisterDailyRepository.save(cashRegisterDaily);
    }

    @Override
    public boolean existsCashRegisterDailiesByCashRegister(boolean cash) {
        return cashRegisterDailyRepository.existsCashRegisterDailiesByCashRegister(cash);
    }

    @Override
    public int countByPageable(boolean pag) {
        try {
            return cashRegisterDailyRepository.countByPageable(pag);
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

}
