package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.customer.RequestAddCustomerDTO;
import com.company.storeapi.model.payload.request.customer.RequestUpdateCustomerDTO;
import com.company.storeapi.model.payload.response.customer.ResponseCustomerDTO;
import com.company.storeapi.services.customer.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Customer rest api.
 */
@RestController
@RequestMapping(value = "/api/customer")
@CrossOrigin({"*"})
public class CustomerRestApi {

    private final CustomerService customerService;

    /**
     * Instantiates a new Customer rest api.
     *
     * @param customerService the customer service
     */
    public CustomerRestApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Gets all product.
     *
     * @return the all product
     * @throws ServiceException the service exception
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseCustomerDTO> getAllProduct() {
        return customerService.getAllCustomers();
    }


    /**
     * Gets product by id.
     *
     * @param id the id
     * @return the product by id
     * @throws ServiceException the service exception
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCustomerDTO> getProductById(@PathVariable("id") String  id)
            {
        ResponseCustomerDTO entity = customerService.validateAndGetCustomerById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Create response entity.
     *
     * @param customerDTO the customer dto
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCustomerDTO> create(@RequestBody RequestAddCustomerDTO customerDTO) {
        ResponseCustomerDTO created = customerService.saveCustomer(customerDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Update product response entity.
     *
     * @param requestUpdateCustomerDTO the request update customer dto
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCustomerDTO> update (@RequestBody RequestUpdateCustomerDTO requestUpdateCustomerDTO) {
        ResponseCustomerDTO update = customerService.updateCustomer(requestUpdateCustomerDTO.getId(), requestUpdateCustomerDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/status/{status}")
    public  ResponseEntity<ResponseCustomerDTO> updateStatus(@PathVariable String id, @PathVariable Status status){
        ResponseCustomerDTO update= customerService.updateStatus(id,status);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }
}
