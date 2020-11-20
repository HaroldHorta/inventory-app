package com.company.storeapi.services.finances.cashRegister.impl;

import com.company.storeapi.core.mapper.CashRegisterMapper;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.entity.finance.CashRegister;
import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.payload.response.finance.CreditCapital;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.repositories.finances.cashRegister.facade.CashRegisterRepositoryFacade;
import com.company.storeapi.repositories.tickey.facade.TicketRepositoryFacade;
import com.company.storeapi.services.finances.cashRegister.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashRegisterServiceImpl implements CashRegisterService {

    private final CashRegisterRepositoryFacade cashRegisterRepositoryFacade;
    private final CashRegisterMapper cashRegisterMapper;
    private final TicketRepositoryFacade ticketRepositoryFacade;


    @Override
    public List<ResponseCashRegisterDTO> getCashRegister() {
        List<CashRegister> cashRegisters = cashRegisterRepositoryFacade.getCashRegister();
        return cashRegisters.stream().map(cashRegisterMapper::DtoChasRegisterDocument).collect(Collectors.toList());
    }

    @Override
    public ResponseCashRegisterDTO saveCashRegister(CashRegister cashRegister) {
        double cashPayment;
        double transactionPayment;
        AtomicReference<Double> creditCashPayment = new AtomicReference<>((double) 0);
        AtomicReference<Double> transactionCashPayment = new AtomicReference<>((double) 0);

        AtomicReference<Double> creditPayment = new AtomicReference<>((double) 0);
        List<Ticket> ticket = ticketRepositoryFacade.getAllTicketByCashRegister();
        Set<CreditCapital> creditCapitals = new LinkedHashSet<>();


        cashPayment = ticket.stream().filter(t -> t.getPaymentType() == PaymentType.CASH).mapToDouble(Ticket::getCashPayment).sum();
        transactionPayment = ticket.stream().filter(t -> t.getPaymentType() == PaymentType.TRANSACTION).mapToDouble(Ticket::getTransactionPayment).sum();

        ticket.forEach(tick -> {
            if (tick.getPaymentType() == PaymentType.CREDIT) {
                tick.getCreditCapital().forEach(cap -> {
                    if (!cap.isCashRegister()) {
                        if(cap.getPaymentType() == PaymentType.CASH){
                            creditCashPayment.updateAndGet(v -> new Double((double) (v + cap.getCashCreditCapital())));
                        }
                        if(cap.getPaymentType() == PaymentType.TRANSACTION){
                            transactionCashPayment.updateAndGet(v -> new Double((double) (v + cap.getCashCreditCapital())));
                        }
                        cap.setCashRegister(true);
                        creditCapitals.add(cap);
                    }
                });
                tick.setCreditCapital(creditCapitals);
                ticketRepositoryFacade.saveTicket(tick);
            }
        });



        return null;
    }
}
