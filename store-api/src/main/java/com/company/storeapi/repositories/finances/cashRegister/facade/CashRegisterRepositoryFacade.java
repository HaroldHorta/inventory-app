package com.company.storeapi.repositories.finances.cashRegister.facade;

import com.company.storeapi.model.entity.finance.CashRegister;

import java.util.List;

public interface CashRegisterRepositoryFacade  {

    List<CashRegister> getCashRegister();

    CashRegister saveCashRegister ( CashRegister cashRegister);
}
