package com.company.storeapi.repositories.tickey.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.repositories.tickey.TicketRepository;
import com.company.storeapi.repositories.tickey.facade.TicketRepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TicketRepositoryFacadeImpl implements TicketRepositoryFacade {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> getAllTicket() {
       try {
           return Optional.of(ticketRepository.findAll())
                   .orElseThrow(()-> new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND,"No se encontraron registros de ticket"));
       }catch (IllegalArgumentException ie){
           throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
       }catch (DataAccessException er){
           throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
       }
    }

    @Override
    public List<Ticket> getAllTicketByCashRegister() {
        try {
            return Optional.of(ticketRepository.findAll().stream().filter(ticket -> !ticket.isCashRegister()).collect(Collectors.toList()))
                    .orElseThrow(()-> new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND,"No se encontraron registros de ticket"));
        }catch (IllegalArgumentException ie){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public Ticket validateAndGetTicketById(String id) {
        return ticketRepository.findById(id).orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "NO se encontraron productos con el id " + id));

    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        try {
            return ticketRepository.save(ticket);
        }catch (IllegalArgumentException ie){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"Error al guardar el producto");
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public Ticket findTicketByOrder(String id) {
        return ticketRepository.findTicketByOrder(id);
    }

    @Override
    public List<Ticket> findTicketByCustomer_NroDocument(String nroDocument) {
        try {
            return Optional.of(ticketRepository.findTicketByCustomer_NroDocument(nroDocument))
                    .orElseThrow(()-> new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND,"El cliente no tiene ticket asosiados"));
        }catch (IllegalArgumentException ie){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public boolean existsByCashRegister(boolean cash) {
        return ticketRepository.existsByCashRegister(cash);
    }
}
