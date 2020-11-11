package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.payload.request.ticket.RequestAddTicketDTO;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;
import com.company.storeapi.services.ticket.TicketServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin({"*"})
@RequiredArgsConstructor
public class TicketRestApi {

    private final TicketServices ticketServices;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseTicketDTO> getAllTicket() throws ServiceException {
        return ticketServices.getAllTicket();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseTicketDTO> getTicketById(@PathVariable String id) throws ServiceException{
        ResponseTicketDTO responseTicketDTO = ticketServices.validateAndGetTicketById(id);
        return new ResponseEntity<>(responseTicketDTO,new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "nroDocument/{nroDocument}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseTicketDTO> findTicketByCustomerNroDocument(@PathVariable String nroDocument) throws ServiceException{
        return ticketServices.findTicketByCustomer_NroDocument(nroDocument);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseTicketDTO> saveTicket(@RequestBody RequestAddTicketDTO requestAddTicketDTO) throws ServiceException{
        ResponseTicketDTO entity = ticketServices.saveTicket(requestAddTicketDTO);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.CREATED);
    }

}
