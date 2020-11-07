package com.company.storeapi.repositories.customer.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.repositories.customer.CustomerRepository;
import com.company.storeapi.repositories.customer.facade.CustomerRepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CustomerRepositoryFacadeImpl implements CustomerRepositoryFacade {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        try {
            return Optional.of(customerRepository.findAll())
                    .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de clientes"));
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(String id) throws ServiceException {
        try {
            customerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        } catch (DataAccessException er) {
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD, er);
        }
    }

    @Override
    public Customer validateAndGetCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de clientes"));
    }


    @Override
    public Customer findByNroDocument(String nroDocument) {
        return Optional.ofNullable(customerRepository.findCustomerByNroDocument(nroDocument))
                .orElseThrow(() -> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de clientes con el numero: " + nroDocument));
    }

    @Override
    public Boolean validateAndGetCustomerByNroDocument(String nroDocument) {
        return customerRepository.existsCustomerByNroDocument(nroDocument);
    }

    @Override
    public Boolean validateAndGetCustomerbyEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }
}
