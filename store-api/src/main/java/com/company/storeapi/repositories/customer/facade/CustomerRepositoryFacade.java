package com.company.storeapi.repositories.customer.facade;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.entity.Customer;

import java.util.List;

public interface CustomerRepositoryFacade {

    List<Customer> getAllCustomers();

    Customer saveCustomer(Customer customer);

    void deleteCustomer(String id) throws ServiceException;

    Customer validateAndGetCustomerById(String id);

    Customer findByNroDocument (String nroDocument);
}
