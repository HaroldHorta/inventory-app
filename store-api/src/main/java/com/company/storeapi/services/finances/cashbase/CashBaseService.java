package com.company.storeapi.services.finances.cashbase;

import com.company.storeapi.model.payload.response.finance.ResponseCashBase;


public interface CashBaseService {

    ResponseCashBase findCashBaseByUltimate();

    ResponseCashBase save (double valueCashBase);
}
