package com.company.storeapi.services.ticket.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.TicketMapper;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.enums.TicketStatus;
import com.company.storeapi.model.payload.request.ticket.RequestAddTicketDTO;
import com.company.storeapi.model.payload.response.finance.CreditCapital;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;
import com.company.storeapi.repositories.finances.cashRegisterDaily.facade.CashRegisterDailyRepositoryFacade;
import com.company.storeapi.repositories.tickey.facade.TicketRepositoryFacade;
import com.company.storeapi.services.ticket.TicketServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServicesImpl implements TicketServices {

    private final TicketRepositoryFacade ticketRepositoryFacade;
    private final TicketMapper ticketMapper;
    private final CashRegisterDailyRepositoryFacade cashRegisterDailyRepositoryFacade;

    @Override
    public List<ResponseTicketDTO> getAllTicket() {
        return ticketRepositoryFacade.getAllTicket().stream().map(ticketMapper::toTicketDto).collect(Collectors.toList());
    }

    @Override
    public ResponseTicketDTO validateAndGetTicketById(String id) {
        return ticketMapper.toTicketDto(ticketRepositoryFacade.validateAndGetTicketById(id));
    }

    @Override
    public ResponseTicketDTO saveTicket(RequestAddTicketDTO requestAddTicketDTO) {
        return ticketMapper.toTicketDto(ticketRepositoryFacade.saveTicket(ticketMapper.toTicket(requestAddTicketDTO)));
    }

    @Override
    public List<ResponseTicketDTO> findTicketByCustomer_NroDocument(String nroDocument) {
        return ticketRepositoryFacade.findTicketByCustomer_NroDocument(nroDocument).stream().map(ticketMapper::toTicketDto).collect(Collectors.toList());
    }

    @Override
    public ResponseTicketDTO updateCreditCapital(String idTicket, double creditCapital, PaymentType creditPayment) {

        if (creditCapital <= 0) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "El abono debe ser superior a 0");

        }

        Ticket ticket = ticketRepositoryFacade.validateAndGetTicketById(idTicket);
        CashRegisterDaily cashRegisterDaily = cashRegisterDailyRepositoryFacade.findCashBaseByUltimate();

        if (ticket.getTicketStatus() == TicketStatus.PAYED) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La orden ha sido cancelada en su totalidad");
        }

        double credit = ticket.getOutstandingBalance() - creditCapital;

        Set<CreditCapital> creditCapitals = ticket.getCreditCapitals();

        CreditCapital creditCap = new CreditCapital();
        creditCap.setCashCreditCapital(creditCapital);
        creditCap.setTransactionCreditCapital(0);

        cashRegisterDaily.setCashCreditCapital(cashRegisterDaily.getCashCreditCapital() + creditCapital);
        if (creditPayment == PaymentType.TRANSACTION) {
            creditCap.setCashCreditCapital(0);
            creditCap.setTransactionCreditCapital(creditCapital);

            cashRegisterDaily.setTransactionCreditCapital(cashRegisterDaily.getTransactionCreditCapital() + creditCapital);

        }
        creditCap.setCreatAt(new Date());
        creditCap.setPaymentType(creditPayment);
        creditCapitals.add(creditCap);
        ticket.setCreditCapitals(creditCapitals);
        ticket.setOutstandingBalance(credit);

        if (credit <= 0) {
            ticket.setOutstandingBalance(0);
            ticket.setTicketStatus(TicketStatus.PAYED);
            ticket.setCashRegister(false);
        }


        cashRegisterDailyRepositoryFacade.save(cashRegisterDaily);
        return ticketMapper.toTicketDto(ticketRepositoryFacade.saveTicket(ticket));

    }
}
