package com.company.storeapi.repositories.finances.cashRegisterHistory.facade;

import com.company.storeapi.model.entity.finance.CashRegisterHistory;

import java.util.List;

public interface CashRegisterRepositoryFacadeHistory {

    List<CashRegisterHistory> getCashRegister();

    CashRegisterHistory saveCashRegister (CashRegisterHistory cashRegisterHistory);
}
