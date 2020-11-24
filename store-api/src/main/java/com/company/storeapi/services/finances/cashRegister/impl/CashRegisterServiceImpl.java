package com.company.storeapi.services.finances.cashRegister.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.CashRegisterMapper;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.entity.finance.CashRegister;
import com.company.storeapi.model.payload.response.finance.CreditCapital;
import com.company.storeapi.model.payload.response.finance.ResponseCashBase;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.repositories.finances.cashRegister.facade.CashRegisterRepositoryFacade;
import com.company.storeapi.repositories.tickey.facade.TicketRepositoryFacade;
import com.company.storeapi.services.finances.cashBase.CashBaseService;
import com.company.storeapi.services.finances.cashRegister.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    @Override
    public List<ResponseCashRegisterDTO> getCashRegister() {
        List<CashRegister> cashRegisters = cashRegisterRepositoryFacade.getCashRegister();
        return cashRegisters.stream().map(cashRegisterMapper::DtoChasRegisterDocument).collect(Collectors.toList());
    }

    @Override
    public ResponseCashRegisterDTO saveCashRegister() {

        CashRegister cashRegister = new CashRegister();

        List<Ticket> tickets = ticketRepositoryFacade.getAllTicketByCashRegister();

        ResponseCashBase cashBase = cashBaseService.findCashBaseByUltimate();
        List<Ticket> ticketsCreditFalse = ticketRepositoryFacade.getAllTicketByCreditCapitalByCashRegister(false);
        cashRegister.setDailyCashBase(cashBase.getDailyCashBase());

        if (!tickets.isEmpty()) {

            tickets.forEach(ticket -> {
                if (ticket.getCreditCapital().isEmpty()) {

                    double dailyCashSales = tickets.stream().mapToDouble(Ticket::getCashPayment).sum();
                    cashRegister.setDailyCashSales(dailyCashSales);

                    double dailyTransactionsSales = tickets.stream().mapToDouble(Ticket::getTransactionPayment).sum();
                    cashRegister.setDailyTransactionsSales(dailyTransactionsSales);

                    double dailyCreditSales = tickets.stream().mapToDouble(Ticket::getCreditPayment).sum();
                    cashRegister.setDailyCreditSales(dailyCreditSales);

                    double totalSales = dailyCashSales + dailyTransactionsSales + dailyCreditSales;
                    cashRegister.setTotalSales(totalSales);

                    // falta el servicio para registrar las salidas
                    cashRegister.setMoneyOut(0);
                    cashRegister.setCashCreditCapital(0);
                    cashRegister.setTransactionCreditCapital(0);


                    tickets.forEach(tick -> {
                        tick.setCashRegister(true);
                        ticketRepositoryFacade.saveTicket(tick);
                    });
                }
                if (!ticketsCreditFalse.isEmpty()) {

                    tickets.forEach(tick -> {
                        cashRegister.setDailyCashSales(0);

                        cashRegister.setDailyTransactionsSales(0);

                        cashRegister.setDailyCreditSales(0);

                        cashRegister.setTotalSales(0);

                        // falta el servicio para registrar las salidas
                        cashRegister.setMoneyOut(0);

                        tick.getCreditCapital().forEach(c -> {

                            if (!c.isCashRegister()) {
                                Set<CreditCapital> creditCapital = tick.getCreditCapital();
                                double cashCreditCapital = +c.getCashCreditCapital();
                                double transactionCreditCapital = +c.getTransactionCreditCapital();
                                cashRegister.setCashCreditCapital(cashCreditCapital);
                                cashRegister.setTransactionCreditCapital(transactionCreditCapital);
                                c.setCashRegister(true);
                                creditCapital.add(c);
                                tick.setCreditCapital(creditCapital);
                            }
                            if (tick.getOutstandingBalance() == 0) {
                                tick.setCashRegister(true);
                            }
                        });
                        ticketRepositoryFacade.saveTicket(tick);
                    });
                }

                if (tickets.isEmpty()) {
                    throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encuentran movimientos");
                }

            });

        }

//        List<Ticket> ticketsCreditTrue = ticketRepositoryFacade.getAllTicketByCreditCapitalByCashRegister(true);
//        List<Ticket> ticketsCreditFalse = ticketRepositoryFacade.getAllTicketByCreditCapitalByCashRegister(false);
//        ResponseCashBase cashBase = cashBaseService.findCashBaseByUltimate();
//

//        if (ticketsCreditTrue.isEmpty() && ticketsCreditFalse.isEmpty()) {
//
//            getDataGeneralCashRegister(cashRegister, tickets);
//            cashRegister.setCashCreditCapital((double) 0);
//            cashRegister.setTransactionCreditCapital((double) 0);
//
//            cashRegister.setDailyCashBase(cashBase.getDailyCashBase());
//
//            tickets.forEach(tick -> {
//                tick.setCashRegister(true);
//                ticketRepositoryFacade.saveTicket(tick);
//            });
//        }
//
//        if (!tickets.isEmpty() && ticketsCreditFalse.isEmpty()) {
//            cashRegister.setDailyCashBase(cashBase.getDailyCashBase());
//
//            getDataGeneralCashRegister(cashRegister, tickets);
//

//
//        }

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
