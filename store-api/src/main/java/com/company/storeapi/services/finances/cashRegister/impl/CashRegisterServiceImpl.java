package com.company.storeapi.services.finances.cashRegister.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.CashRegisterMapper;
import com.company.storeapi.model.entity.finance.CashBase;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.entity.finance.CashRegisterHistory;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.repositories.finances.cashBase.facade.CashBaseRepositoryFacade;
import com.company.storeapi.repositories.finances.cashRegisterHistory.facade.CashRegisterRepositoryFacadeHistory;
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

    private final CashRegisterRepositoryFacadeHistory cashRegisterRepositoryFacadeHistory;
    private final CashRegisterMapper cashRegisterMapper;
    private final CashRegisterDailyRepositoryFacade cashRegisterDailyRepositoryFacade;
    private final CashBaseRepositoryFacade cashBaseRepositoryFacade;

    @Override
    public List<ResponseCashRegisterDTO> getCashRegister() {
        List<CashRegisterHistory> cashRegisterHistories = cashRegisterRepositoryFacadeHistory.getCashRegister();
        return cashRegisterHistories.stream().map(cashRegisterMapper::DtoChasRegisterDocument).collect(Collectors.toList());
    }

    @Override
    public ResponseCashRegisterDTO saveCashRegister() {

        CashRegisterDaily cashRegisterDaily = cashRegisterDailyRepositoryFacade.findCashBaseByUltimate();
        CashBase cashBase = cashBaseRepositoryFacade.findCashBaseByUltime();
        double totalSales = cashRegisterDaily.getDailyCashSales() + cashRegisterDaily.getDailyTransactionsSales() + cashRegisterDaily.getDailyCreditSales();

        if (totalSales == 0) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se han registrado movimientos");
        }

        CashRegisterHistory cashRegisterHistory = new CashRegisterHistory();
        cashRegisterHistory.setDailyCashBase(cashBase.getDailyCashBase());
        cashRegisterHistory.setDailyCashBase(cashRegisterDaily.getDailyCashSales());
        cashRegisterHistory.setDailyTransactionsSales(cashRegisterDaily.getDailyTransactionsSales());
        cashRegisterHistory.setDailyCreditSales(cashRegisterDaily.getDailyCreditSales());
        cashRegisterHistory.setTotalSales(totalSales);

        cashRegisterHistory.setMoneyOut(0);

        cashRegisterHistory.setCashCreditCapital(cashRegisterDaily.getCashCreditCapital());
        cashRegisterHistory.setTransactionCreditCapital(cashRegisterDaily.getTransactionCreditCapital());

        cashRegisterHistory.setCreateAt(new Date());


        cashRegisterRepositoryFacadeHistory.saveCashRegister(cashRegisterHistory);

        cashRegisterDaily.setDailyCashSales(0);
        cashRegisterDaily.setDailyTransactionsSales(0);
        cashRegisterDaily.setDailyCreditSales(0);
        cashRegisterDaily.setCashCreditCapital(0);
        cashRegisterDaily.setTransactionCreditCapital(0);
        cashRegisterDaily.setCashRegister(true);


        cashRegisterDailyRepositoryFacade.save(cashRegisterDaily);


        return null;
    }

    public ResponseCashRegisterDTO getResponseCashRegister(CashRegisterHistory cashRegisterHistory) {
        ResponseCashRegisterDTO responseCashRegisterDTO = new ResponseCashRegisterDTO();
        responseCashRegisterDTO.setDailyCashBase(cashRegisterHistory.getDailyCashBase());
        responseCashRegisterDTO.setDailyCashSales(cashRegisterHistory.getDailyCashSales());
        responseCashRegisterDTO.setDailyTransactionsSales(cashRegisterHistory.getDailyTransactionsSales());
        responseCashRegisterDTO.setDailyCreditSales(cashRegisterHistory.getDailyCreditSales());
        responseCashRegisterDTO.setTotalSales(cashRegisterHistory.getTotalSales());
        responseCashRegisterDTO.setMoneyOut(cashRegisterHistory.getMoneyOut());
        responseCashRegisterDTO.setCashCreditCapital(cashRegisterHistory.getCashCreditCapital());
        responseCashRegisterDTO.setTransactionCreditCapital(cashRegisterHistory.getTransactionCreditCapital());
        responseCashRegisterDTO.setCreateAt(cashRegisterHistory.getCreateAt());

        return responseCashRegisterDTO;
    }
}
