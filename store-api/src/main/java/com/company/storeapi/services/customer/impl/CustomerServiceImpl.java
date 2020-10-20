package com.company.storeapi.services.customer.impl;

import com.company.storeapi.core.mapper.CustomerMapper;
import com.company.storeapi.model.entity.CountingGeneral;
import com.company.storeapi.model.payload.request.customer.RequestAddCustomerDTO;
import com.company.storeapi.model.payload.request.customer.RequestUpdateCustomerDTO;
import com.company.storeapi.model.payload.response.customer.ResponseCustomerDTO;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.repositories.customer.facade.CustomerRepositoryFacade;
import com.company.storeapi.services.countingGeneral.CountingGeneralService;
import com.company.storeapi.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Customer service.
 */
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepositoryFacade customerRepositoryFacade;
    private final CustomerMapper customerMapper;
    private final CountingGeneralService countingGeneralService;

    @Override
    public List<ResponseCustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepositoryFacade.getAllCustomers();
        return customerList.stream().map(customerMapper::toCustomerDto).collect(Collectors.toList());
    }

    @Override
    public ResponseCustomerDTO saveCustomer(RequestAddCustomerDTO requestAddCustomerDTO) {

        ResponseCustomerDTO responseCustomerDTO =   customerMapper.toCustomerDto(customerRepositoryFacade.saveCustomer(customerMapper.toCustomer(requestAddCustomerDTO)));

        List<CountingGeneral> counting = countingGeneralService.getAllCountingGeneral();

        if((counting.size() ==0)){
            CountingGeneral c = new CountingGeneral();

            c.setQuantity_of_customer(1);
            countingGeneralService.saveCountingGeneral(c);

        }  else{
            counting.forEach(p->{
                CountingGeneral countingGeneral = countingGeneralService.validateCountingGeneral(p.getId());

                countingGeneral.setQuantity_of_customer(p.getQuantity_of_customer()+1);

                countingGeneralService.saveCountingGeneral(countingGeneral);
            });
        }

        return responseCustomerDTO;
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepositoryFacade.deleteCustomer(id);
    }

    @Override
    public ResponseCustomerDTO updateCustomer(String id, RequestUpdateCustomerDTO requestUpdateCustomerDTO) {
        Customer customer = customerRepositoryFacade.validateAndGetCustomerById(id);
        customerMapper.updateCustomerFromDto(requestUpdateCustomerDTO, customer);
        return customerMapper.toCustomerDto(customerRepositoryFacade.saveCustomer(customer));
    }

    @Override
    public ResponseCustomerDTO validateAndGetCustomerById(String  id) {
        return customerMapper.toCustomerDto(customerRepositoryFacade.validateAndGetCustomerById(id));
    }


}
