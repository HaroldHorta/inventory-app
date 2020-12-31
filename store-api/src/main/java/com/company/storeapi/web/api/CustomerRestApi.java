package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.customer.RequestAddCustomerDTO;
import com.company.storeapi.model.payload.request.customer.RequestUpdateCustomerDTO;
import com.company.storeapi.model.payload.response.customer.ResponseCustomerDTO;
import com.company.storeapi.model.payload.response.customer.ResponseListCustomerPaginationDto;
import com.company.storeapi.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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

    @Value("${spring.size.pagination}")
    private int size;

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
    public List<ResponseCustomerDTO> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/customerFilter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListCustomerPaginationDto getAllCustomerFilter() {
        return customerService.getCustomerPageable();
    }


    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListCustomerPaginationDto getAllCustomerPage(@Param(value = "page") int page) {
        Pageable requestedPage = PageRequest.of(page, size);
        return customerService.getCustomerPageable(requestedPage);
    }

    /**
     * Gets product by id.
     *
     * @param id the id
     * @return the product by id
     * @throws ServiceException the service exception
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCustomerDTO> getCustomerById(@PathVariable("id") String  id)
            {
        ResponseCustomerDTO entity = customerService.validateAndGetCustomerById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/nroDocument/{nroDocument}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCustomerDTO> getCustomerByNroDocument(@PathVariable("nroDocument") String  nroDocument)
    {
        ResponseCustomerDTO entity = customerService.getCustomerByNroDocument(nroDocument);
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
