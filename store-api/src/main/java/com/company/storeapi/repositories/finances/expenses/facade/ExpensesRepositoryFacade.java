package com.company.storeapi.repositories.finances.expenses.facade;

import com.company.storeapi.model.entity.finance.Expenses;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpensesRepositoryFacade {

    List<Expenses> findAllExpenses();

    List<Expenses> findAllByPageable (boolean pag, Pageable pageable);

    Expenses saveExpenses (Expenses expenses);

    Expenses findExpensesById (String id);


}
