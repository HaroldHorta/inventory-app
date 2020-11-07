package com.company.storeapi.repositories.customer;

import com.company.storeapi.model.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CustomerRepository extends MongoRepository<Customer,String> {

    Customer findCustomerByNroDocument (String nroDocument);
    Boolean existsCustomerByNroDocument (String nroDocument);
    Boolean existsCustomerByEmail(String email);
}
