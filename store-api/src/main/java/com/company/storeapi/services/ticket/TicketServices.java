package com.company.storeapi.services.ticket;

import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.payload.request.ticket.RequestAddTicketDTO;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;

import java.util.List;

public interface TicketServices {

    List<ResponseTicketDTO> getAllTicket();

    ResponseTicketDTO validateAndGetTicketById (String id);

    ResponseTicketDTO saveTicket(RequestAddTicketDTO requestAddTicketDTO);

    List<ResponseTicketDTO> findTicketByCustomer_NroDocument (String nroDocument);

    ResponseTicketDTO updateCreditCapital(String idTicket, Double creditCapital, PaymentType creditPayment);

}
