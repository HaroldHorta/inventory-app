package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.finance.CashRegister;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;
import com.company.storeapi.services.ticket.TicketServices;
import org.apache.coyote.Response;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CashRegisterMapper {

    @Autowired
    private TicketServices ticketServices;

    public abstract ResponseCashRegisterDTO DtoChasRegisterDocument(CashRegister cashRegister);

//    public CashRegister toCashRegister(ResponseCashRegisterDTO responseCashRegisterDTO) {
//
//       // List<ResponseTicketDTO> responseCashRegisterDTOS = ticketServices.getAllTicketByCashRegister();
//       // responseCashRegisterDTOS.forEach(ticketDTO -> {
//         //  double totalCash += ticketDTO.getC.
//        });
//
//        return null;
//    }
}
