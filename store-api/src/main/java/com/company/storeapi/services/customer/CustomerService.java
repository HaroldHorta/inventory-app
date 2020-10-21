package com.company.storeapi.services.customer;

import com.company.storeapi.model.payload.request.customer.RequestAddCustomerDTO;
import com.company.storeapi.model.payload.request.customer.RequestUpdateCustomerDTO;
import com.company.storeapi.model.payload.response.customer.ResponseCustomerDTO;

import java.util.List;

/**
 * The interface Customer service.
 */
public interface CustomerService {

    /**
     * Gets all customers.
     *
     * @return the all customers
     */
    List<ResponseCustomerDTO> getAllCustomers();

    /**
     * Save customer response customer dto.
     *
     * @param customer the customer
     * @return the response customer dto
     */
    ResponseCustomerDTO saveCustomer(RequestAddCustomerDTO customer);

    /**
     * Delete customer.
     *
     * @param id the id
     */
    void deleteCustomer(String id);

    /**
     * Update customer response customer dto.
     *
     * @param id                       the id
     * @param requestUpdateCustomerDTO the request update customer dto
     * @return the response customer dto
     */
    ResponseCustomerDTO updateCustomer(String id, RequestUpdateCustomerDTO requestUpdateCustomerDTO);

    /**
     * Validate and get customer by id response customer dto.
     *
     * @param id the id
     * @return the response customer dto
     */
    ResponseCustomerDTO validateAndGetCustomerById(String id);
}
