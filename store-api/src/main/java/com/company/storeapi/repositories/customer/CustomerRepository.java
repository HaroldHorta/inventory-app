package com.company.storeapi.repositories.customer;

import com.company.storeapi.model.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer,String> {

    Customer findCustomerByNroDocument (String nroDocument);
}
