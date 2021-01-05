package com.company.storeapi.services.finances.cashRegister;

import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.model.payload.response.finance.ResponseListCashRegisterDailyPaginationDto;
import com.company.storeapi.model.payload.response.finance.ResponseListExpensesPaginationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CashRegisterService {

    ResponseCashRegisterDTO saveCashRegister ();

    ResponseListCashRegisterDailyPaginationDto getCashRegisterPageable();

    ResponseListCashRegisterDailyPaginationDto getCashRegisterPageable(Pageable pageable);
}
