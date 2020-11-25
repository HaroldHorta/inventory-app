package com.company.storeapi.services.finances.cashRegister.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.CashRegisterMapper;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.entity.finance.CashBase;
import com.company.storeapi.model.entity.finance.CashRegister;
import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.entity.CreditCapital;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.repositories.finances.cashBase.facade.CashBaseRepositoryFacade;
import com.company.storeapi.repositories.finances.cashRegister.facade.CashRegisterRepositoryFacade;
import com.company.storeapi.repositories.finances.creditCapital.facade.CreditCapitalRepositoryFacade;
import com.company.storeapi.repositories.tickey.facade.TicketRepositoryFacade;
import com.company.storeapi.services.finances.cashRegister.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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
        List<CashRegister> cashRegisters = cashRegisterRepositoryFacade.getCashRegister();
        return cashRegisters.stream().map(cashRegisterMapper::DtoChasRegisterDocument).collect(Collectors.toList());
    }

    @Override
    public ResponseCashRegisterDTO saveCashRegister() {

        CashRegister cashRegister = new CashRegister();
        List<Ticket> tickets = ticketRepositoryFacade.getAllTicketByCashRegister();
        boolean creditTrue = creditCapitalRepositoryFacade.existsCreditCapitalByCashRegister();
        CashBase cashBase = cashBaseRepositoryFacade.findCashBaseByUltime();

        cashRegister.setDailyCashBase(cashBase.getDailyCashBase());
        cashRegister.setMoneyOut(0);
        cashRegister.setCreateAt(new Date());

        if (tickets.isEmpty()) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "No se registran movimientos");
        }

        AtomicReference<Double> dailyCashSales = new AtomicReference<>((double) 0);
        AtomicReference<Double> dailyTransactionsSales = new AtomicReference<>((double) 0);
        AtomicReference<Double> dailyCreditSales = new AtomicReference<>((double) 0);
        AtomicReference<Double> cashCreditCapital = new AtomicReference<>((double) 0);
        AtomicReference<Double> transactionCreditCapital = new AtomicReference<>((double) 0);
        tickets.forEach(ticket -> {
            List<CreditCapital> creditCapitals = creditCapitalRepositoryFacade.findCreditCapitalByTicket(ticket.getId());


            if (ticket.getPaymentType() != PaymentType.CREDIT && !ticket.isCashRegister()) {
                dailyCashSales.set(tickets.stream().mapToDouble(Ticket::getCashPayment).sum());
                dailyTransactionsSales.set(tickets.stream().mapToDouble(Ticket::getTransactionPayment).sum());
                ticket.setCashRegister(true);
            }

            if (ticket.getPaymentType() == PaymentType.CREDIT && !ticket.isCashRegisterCredit()) {
                double x = 0;
                 x = x + ticket.getCreditPayment();
                dailyCreditSales.set(x);
                ticket.setCashRegisterCredit(true);
            }

            if (!creditCapitals.isEmpty()) {
                cashCreditCapital.set(creditCapitals.stream().mapToDouble(CreditCapital::getCashCreditCapital).sum());
                transactionCreditCapital.set(creditCapitals.stream().mapToDouble(CreditCapital::getTransactionCreditCapital).sum());
            }

            cashRegister.setDailyCashSales(dailyCashSales);
            cashRegister.setDailyTransactionsSales(dailyTransactionsSales);
            cashRegister.setDailyCreditSales(dailyCreditSales);
            cashRegister.setCashCreditCapital(cashCreditCapital);
            cashRegister.setTransactionCreditCapital(transactionCreditCapital);
            cashRegister.setTotalSales(0);

            ticketRepositoryFacade.saveTicket(ticket);
        });

        return getResponseCashRegister(cashRegisterRepositoryFacade.saveCashRegister(cashRegister));
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
