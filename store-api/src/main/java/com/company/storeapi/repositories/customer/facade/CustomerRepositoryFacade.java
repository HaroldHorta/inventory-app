package com.company.storeapi.repositories.customer.facade;

import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.enums.Status;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerRepositoryFacade {

    List<Customer> getAllCustomers();

    List<Customer> findAllPageable (Status status,Pageable pageable);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(String id);

    Customer validateAndGetCustomerById(String id);

    Customer findByNroDocument (String nroDocument);

    Boolean validateAndGetCustomerByNroDocument(String id);

    Boolean validateAndGetCustomerByEmail(String email);

    Boolean existsCustomerById (String id);

    int countByStatus (Status status);

}
