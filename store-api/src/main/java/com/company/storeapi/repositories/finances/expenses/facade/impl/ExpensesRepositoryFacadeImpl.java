package com.company.storeapi.repositories.finances.expenses.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.finance.Expenses;
import com.company.storeapi.repositories.finances.expenses.ExpensesRepository;
import com.company.storeapi.repositories.finances.expenses.facade.ExpensesRepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExpensesRepositoryFacadeImpl implements ExpensesRepositoryFacade {

    private final ExpensesRepository expensesRepository;


    @Override
    public List<Expenses> findAllExpenses() {
        try {
            return Optional.of(expensesRepository.findAll())
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de gastos"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encuentran registros de gastos");
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public List<Expenses> findAllByPageable(boolean pag, Pageable pageable) {
        try {
            return expensesRepository.findAllByPageable(pag,pageable);
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encuentran registros de gastos");
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public Expenses saveExpenses(Expenses expenses) {
        return expensesRepository.save(expenses);
    }

    @Override
    public Expenses findExpensesById(String id) {
        return expensesRepository.findById(id).orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de gastos co el id: " + id));
    }
}
