package com.company.storeapi.services.finances.cashRegister.impl;

import com.company.storeapi.core.mapper.CashRegisterMapper;
import com.company.storeapi.model.entity.finance.CashBase;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.repositories.finances.cashBase.facade.CashBaseRepositoryFacade;
import com.company.storeapi.repositories.finances.cashRegisterDaily.facade.CashRegisterDailyRepositoryFacade;
import com.company.storeapi.services.finances.cashRegister.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashRegisterServiceImpl implements CashRegisterService {

    private final CashRegisterMapper cashRegisterMapper;
    private final CashRegisterDailyRepositoryFacade cashRegisterDailyRepositoryFacade;
    private final CashBaseRepositoryFacade cashBaseRepositoryFacade;

    @Override
    public List<ResponseCashRegisterDTO> getCashRegister() {
       List<CashRegisterDaily> cashRegisterHistories = cashRegisterDailyRepositoryFacade.findAllCashRegisterDaily();
        return cashRegisterHistories.stream().filter(CashRegisterDaily::isCashRegister).map(cashRegisterMapper::DtoChasRegisterDocument).collect(Collectors.toList());
    }

    @Override
    public ResponseCashRegisterDTO saveCashRegister() {

        CashRegisterDaily cashRegisterDaily = cashRegisterDailyRepositoryFacade.findCashRegisterDailyByUltimate();
        CashBase cashBase = cashBaseRepositoryFacade.findCashBaseByUltime();
        double totalSales = cashRegisterDaily.getDailyCashSales() + cashRegisterDaily.getDailyTransactionsSales() + cashRegisterDaily.getDailyCreditSales();

        cashRegisterDaily.setDailyCashBase(cashBase.getDailyCashBase());
        cashRegisterDaily.setTotalSales(totalSales);
        cashRegisterDaily.setCreateAt(new Date());
        cashRegisterDaily.setCashRegister(true);

        return getResponseCashRegister( cashRegisterDailyRepositoryFacade.save(cashRegisterDaily));
    }

    public ResponseCashRegisterDTO getResponseCashRegister(CashRegisterDaily cashRegisterDaily) {
        ResponseCashRegisterDTO responseCashRegisterDTO = new ResponseCashRegisterDTO();
        responseCashRegisterDTO.setDailyCashBase(cashRegisterDaily.getDailyCashBase());
        responseCashRegisterDTO.setDailyCashSales(cashRegisterDaily.getDailyCashSales());
        responseCashRegisterDTO.setDailyTransactionsSales(cashRegisterDaily.getDailyTransactionsSales());
        responseCashRegisterDTO.setDailyCreditSales(cashRegisterDaily.getDailyCreditSales());
        responseCashRegisterDTO.setTotalSales(cashRegisterDaily.getTotalSales());
        responseCashRegisterDTO.setMoneyOut(cashRegisterDaily.getMoneyOut());
        responseCashRegisterDTO.setCashCreditCapital(cashRegisterDaily.getCashCreditCapital());
        responseCashRegisterDTO.setTransactionCreditCapital(cashRegisterDaily.getTransactionCreditCapital());
        responseCashRegisterDTO.setCreateAt(cashRegisterDaily.getCreateAt());

        return responseCashRegisterDTO;
    }
}
