package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
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

@RestController
@RequestMapping(value = "/api/customer")
@CrossOrigin({"*"})
public class CustomerRestApi {

    private final CustomerService customerService;

    public CustomerRestApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseCustomerDTO> getAllProduct() throws ServiceException {
        return customerService.getAllCustomers();
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCustomerDTO> getProductById(@PathVariable("id") String  id)
            throws ServiceException {
        ResponseCustomerDTO entity = customerService.validateAndGetCustomerById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCustomerDTO> create(@RequestBody RequestAddCustomerDTO customerDTO) throws ServiceException {
        ResponseCustomerDTO created = customerService.saveCustomer(customerDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCustomerDTO> updateProduct (@PathVariable String id, @RequestBody RequestUpdateCustomerDTO requestUpdateCustomerDTO) throws ServiceException{
        ResponseCustomerDTO update = customerService.updateCustomer(id, requestUpdateCustomerDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }
}
