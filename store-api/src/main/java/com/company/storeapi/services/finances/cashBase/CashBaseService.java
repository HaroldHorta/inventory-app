package com.company.storeapi.services.finances.cashBase;

import com.company.storeapi.model.payload.response.finance.ResponseCashBase;


public interface CashBaseService {

    ResponseCashBase findCashBaseByUltimate();

    ResponseCashBase save (double valueCashBase);
}
