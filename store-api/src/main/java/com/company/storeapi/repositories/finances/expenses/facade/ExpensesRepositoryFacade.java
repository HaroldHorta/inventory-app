package com.company.storeapi.repositories.finances.expenses.facade;

import com.company.storeapi.model.entity.finance.Expenses;

import java.util.List;

public interface ExpensesRepositoryFacade {

    List<Expenses> findAllExpenses();

    Expenses saveExpenses (Expenses expenses);

    Expenses findExpensesById (String id);

}
