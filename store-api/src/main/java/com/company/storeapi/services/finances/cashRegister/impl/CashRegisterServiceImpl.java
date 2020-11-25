package com.company.storeapi.services.finances.cashRegister.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.CashRegisterMapper;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.entity.finance.CashRegister;
import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.entity.CreditCapital;
import com.company.storeapi.model.payload.response.finance.ResponseCashBase;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.repositories.finances.cashRegister.facade.CashRegisterRepositoryFacade;
import com.company.storeapi.repositories.finances.creditCapital.facade.CreditCapitalRepositoryFacade;
import com.company.storeapi.repositories.tickey.facade.TicketRepositoryFacade;
import com.company.storeapi.services.finances.cashBase.CashBaseService;
import com.company.storeapi.services.finances.cashRegister.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashRegisterServiceImpl implements CashRegisterService {

    private final CashRegisterRepositoryFacade cashRegisterRepositoryFacade;
    private final CashRegisterMapper cashRegisterMapper;
    private final CashBaseService cashBaseService;
    private final TicketRepositoryFacade ticketRepositoryFacade;
    private final CreditCapitalRepositoryFacade creditCapitalRepositoryFacade;


    @Override
    public List<ResponseCashRegisterDTO> getCashRegister() {
        List<CashRegister> cashRegisters = cashRegisterRepositoryFacade.getCashRegister();
        return cashRegisters.stream().map(cashRegisterMapper::DtoChasRegisterDocument).collect(Collectors.toList());
    }

    @Override
    public ResponseCashRegisterDTO saveCashRegister() {

        CashRegister cashRegister = new CashRegister();

        List<Ticket> tickets = ticketRepositoryFacade.getAllTicketByCashRegister();
        if(!tickets.isEmpty()){

            tickets.forEach(ticket -> {
                List<CreditCapital> creditCapitals = creditCapitalRepositoryFacade.findCreditCapitalByTicket(ticket.getId());
                double h;

                if(!creditCapitals.isEmpty()){
              h = creditCapitals.stream().mapToDouble(CreditCapital::getCashCreditCapital).sum();
                }

            });

        }


return  null;
    }

    public ResponseCashRegisterDTO getResponseCashRegister(CashRegister cashRegister) {
        ResponseCashRegisterDTO responseCashRegisterDTO = new ResponseCashRegisterDTO();
        responseCashRegisterDTO.setDailyCashBase(cashRegister.getDailyCashBase());
        responseCashRegisterDTO.setDailyCashSales(cashRegister.getDailyCashSales());
        responseCashRegisterDTO.setDailyTransactionsSales(cashRegister.getDailyTransactionsSales());
        responseCashRegisterDTO.setDailyCreditSales(cashRegister.getDailyCreditSales());
        responseCashRegisterDTO.setTotalSales(cashRegister.getTotalSales());
        responseCashRegisterDTO.setMoneyOut(cashRegister.getMoneyOut());
        responseCashRegisterDTO.setCashCreditCapital(cashRegister.getCashCreditCapital());
        responseCashRegisterDTO.setTransactionCreditCapital(cashRegister.getTransactionCreditCapital());
        responseCashRegisterDTO.setCreateAt(cashRegister.getCreateAt());

        return responseCashRegisterDTO;
    }
}
