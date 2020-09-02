package com.company.storeapi.services.ticket;

import com.company.storeapi.model.dto.request.ticket.RequestAddTicketDTO;
import com.company.storeapi.model.dto.response.ticket.ResponseTicketDTO;

import java.util.List;

public interface TicketServices {

    List<ResponseTicketDTO> getAllTicket();

    ResponseTicketDTO validateAndGetTicketById (String id);

    ResponseTicketDTO saveTicket(RequestAddTicketDTO requestAddTicketDTO);

}
