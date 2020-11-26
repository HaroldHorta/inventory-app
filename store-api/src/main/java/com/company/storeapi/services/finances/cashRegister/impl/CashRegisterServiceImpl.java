package com.company.storeapi.services.finances.cashRegister.impl;

import com.company.storeapi.core.mapper.CashRegisterMapper;
import com.company.storeapi.model.entity.finance.CashRegisterHistory;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.repositories.finances.cashBase.facade.CashBaseRepositoryFacade;
import com.company.storeapi.repositories.finances.cashRegister.facade.CashRegisterRepositoryFacade;
import com.company.storeapi.repositories.finances.creditCapital.facade.CreditCapitalRepositoryFacade;
import com.company.storeapi.repositories.tickey.facade.TicketRepositoryFacade;
import com.company.storeapi.services.finances.cashRegister.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashRegisterServiceImpl implements CashRegisterService {

    private final CashRegisterRepositoryFacade cashRegisterRepositoryFacade;
    private final CashRegisterMapper cashRegisterMapper;
    private final CashBaseRepositoryFacade cashBaseRepositoryFacade;
    private final TicketRepositoryFacade ticketRepositoryFacade;
    private final CreditCapitalRepositoryFacade creditCapitalRepositoryFacade;


    @Override
    public List<ResponseCashRegisterDTO> getCashRegister() {
        List<CashRegisterHistory> cashRegisterHistories = cashRegisterRepositoryFacade.getCashRegister();
        return cashRegisterHistories.stream().map(cashRegisterMapper::DtoChasRegisterDocument).collect(Collectors.toList());
    }

    @Override
    public ResponseCashRegisterDTO saveCashRegister() {

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
