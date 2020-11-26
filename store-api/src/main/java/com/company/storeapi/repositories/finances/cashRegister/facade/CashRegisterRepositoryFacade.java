package com.company.storeapi.repositories.finances.cashRegister.facade;

import com.company.storeapi.model.entity.finance.CashRegisterHistory;

import java.util.List;

public interface CashRegisterRepositoryFacade  {

    List<CashRegisterHistory> getCashRegister();

    CashRegisterHistory saveCashRegister (CashRegisterHistory cashRegisterHistory);
}
