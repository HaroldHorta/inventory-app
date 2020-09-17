package com.company.storeapi.services.customer;

import com.company.storeapi.model.payload.request.customer.RequestAddCustomerDTO;
import com.company.storeapi.model.payload.request.customer.RequestUpdateCustomerDTO;
import com.company.storeapi.model.payload.response.customer.ResponseCustomerDTO;

import java.util.List;

public interface CustomerService {

    List<ResponseCustomerDTO> getAllCustomers();

    ResponseCustomerDTO saveCustomer(RequestAddCustomerDTO customer);

    void deleteCustomer(String id);

    ResponseCustomerDTO updateCustomer(String id, RequestUpdateCustomerDTO requestUpdateCustomerDTO);

    ResponseCustomerDTO validateAndGetCustomerById(String id);
}
