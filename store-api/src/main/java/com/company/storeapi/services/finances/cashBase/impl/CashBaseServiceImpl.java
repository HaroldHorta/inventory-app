package com.company.storeapi.services.finances.cashBase.impl;

import com.company.storeapi.model.entity.finance.CashBase;
import com.company.storeapi.model.payload.response.finance.ResponseCashBase;
import com.company.storeapi.repositories.finances.cashBase.facade.CashBaseRepositoryFacade;
import com.company.storeapi.services.finances.cashBase.CashBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashBaseServiceImpl implements CashBaseService {

    private final CashBaseRepositoryFacade cashBaseRepositoryFacade;

    @Override
    public ResponseCashBase findCashBaseByUltime() {
        CashBase cashBase = cashBaseRepositoryFacade.findCashBaseByUltime();
        return getResponseCashBase(cashBase);
    }

    @Override
    public ResponseCashBase save(double valueCashBase) {
        if (cashBaseRepositoryFacade.existsByCashRegister(false)) {
            CashBase cashBase = cashBaseRepositoryFacade.findCashBaseByUltime();
            cashBase.setDailyCashBase(valueCashBase);
            ResponseCashBase responseCashBase = getResponseCashBase(cashBase);
            cashBaseRepositoryFacade.save(cashBase);
            return responseCashBase;
        } else {
            CashBase cashBase = new CashBase();
            cashBase.setCreateAt(new Date());
            cashBase.setDailyCashBase(valueCashBase);
            ResponseCashBase responseCashBase = getResponseCashBase(cashBase);
            cashBaseRepositoryFacade.save(cashBase);
            return responseCashBase;
        }
    }

    public ResponseCashBase getResponseCashBase(CashBase cashBase) {
        ResponseCashBase responseCashBase = new ResponseCashBase();
        responseCashBase.setDailyCashBase(cashBase.getDailyCashBase());
        responseCashBase.setCreateAt(cashBase.getCreateAt());
        return responseCashBase;
    }


}
