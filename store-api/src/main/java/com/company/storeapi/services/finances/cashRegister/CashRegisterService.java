package com.company.storeapi.services.finances.cashRegister;

import com.company.storeapi.model.entity.finance.CashRegister;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;

import java.util.List;

public interface CashRegisterService {

    List<ResponseCashRegisterDTO> getCashRegister();

    ResponseCashRegisterDTO saveCashRegister ( CashRegister cashRegister);
}
