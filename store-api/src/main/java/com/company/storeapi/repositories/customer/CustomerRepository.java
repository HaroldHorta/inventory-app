package com.company.storeapi.repositories.customer;

import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer,String> {

    Customer findCustomerByNroDocument (String nroDocument);
    Boolean existsCustomerByNroDocument (String nroDocument);
    Boolean existsCustomerByEmail(String email);
    List<Customer> findAllByStatus (Status status, Pageable pageable);
}
