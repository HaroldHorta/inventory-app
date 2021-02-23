package com.company.storeapi.services.customer.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.CustomerMapper;
import com.company.storeapi.core.util.Util;
import com.company.storeapi.model.entity.CountingGeneral;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.customer.RequestAddCustomerDTO;
import com.company.storeapi.model.payload.request.customer.RequestUpdateCustomerDTO;
import com.company.storeapi.model.payload.response.customer.ResponseCustomerDTO;
import com.company.storeapi.model.payload.response.customer.ResponseListCustomerPaginationDto;
import com.company.storeapi.repositories.countingGeneral.facade.CountingGeneralRepositoryFacade;
import com.company.storeapi.repositories.customer.facade.CustomerRepositoryFacade;
import com.company.storeapi.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Customer service.
 */
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepositoryFacade customerRepositoryFacade;
    private final CustomerMapper customerMapper;
    private final CountingGeneralRepositoryFacade countingGeneralRepositoryFacade;


    Pattern patternEmail = Pattern
            .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Value("${spring.size.pagination}")
    private int size;

    @Override
    public List<ResponseCustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepositoryFacade.getAllCustomers();
        return customerList.stream().map(customerMapper::toCustomerDto).collect(Collectors.toList());
    }

    @Override
    public ResponseCustomerDTO saveCustomer(RequestAddCustomerDTO requestAddCustomerDTO) {

        boolean existDocument = customerRepositoryFacade.validateAndGetCustomerByNroDocument(requestAddCustomerDTO.getNroDocument().trim());
        if (existDocument) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "El numero de cedula ya existe");
        }

        boolean existEmail = customerRepositoryFacade.validateAndGetCustomerByEmail(requestAddCustomerDTO.getEmail().trim());
        if (existEmail) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "El correo ya existe");
        }

        Customer customer = new Customer();
        if (!requestAddCustomerDTO.getName().isEmpty() && !requestAddCustomerDTO.getTypeDocument().toString().isEmpty() && !requestAddCustomerDTO.getNroDocument().isEmpty()) {
            customer.setName(requestAddCustomerDTO.getName());
            customer.setTypeDocument(requestAddCustomerDTO.getTypeDocument());
            customer.setNroDocument(requestAddCustomerDTO.getNroDocument().trim());
            customer.setEmail("N/A");

            if (!requestAddCustomerDTO.getEmail().isEmpty()) {
                Matcher mather = patternEmail.matcher(requestAddCustomerDTO.getEmail().trim());
                if (mather.find()) {
                    customer.setEmail(requestAddCustomerDTO.getEmail().trim());
                } else {
                    throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "Email no valido");
                }
            }

            customer.setAddress(requestAddCustomerDTO.getAddress().isEmpty() ? "N/A" : requestAddCustomerDTO.getAddress().trim());
            customer.setPhone(requestAddCustomerDTO.getPhone().isEmpty() ? "N/A" : requestAddCustomerDTO.getPhone().trim());
            customer.setStatus(Status.ACTIVO);

        } else {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "Los campos nombre, tipo de documento y numero de documento son obligatorios");
        }

        countingGeneralCustomer();

        return customerMapper.toCustomerDto(customerRepositoryFacade.saveCustomer(customer));
    }


    @Override
    public void deleteCustomer(String id) {
        boolean customer = customerRepositoryFacade.existsCustomerById(id);
        if (!customer) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "El cliente no existe");
        }
        customerRepositoryFacade.deleteCustomer(id);
    }

    @Override
    public ResponseCustomerDTO updateCustomer(String id, RequestUpdateCustomerDTO requestUpdateCustomerDTO) {
        Customer customer = customerRepositoryFacade.validateAndGetCustomerById(id);
        customerMapper.updateCustomerFromDto(requestUpdateCustomerDTO, customer);
        return customerMapper.toCustomerDto(customerRepositoryFacade.saveCustomer(customer));
    }

    @Override
    public ResponseCustomerDTO validateAndGetCustomerById(String id) {
        return customerMapper.toCustomerDto(customerRepositoryFacade.validateAndGetCustomerById(id.trim()));
    }

    @Override
    public ResponseCustomerDTO updateStatus(String id, Status status) {
        Customer customer = customerRepositoryFacade.validateAndGetCustomerById(id);
        customer.setStatus(status);
        return customerMapper.toCustomerDto(customerRepositoryFacade.saveCustomer(customer));
    }

    @Override
    public ResponseCustomerDTO getCustomerByNroDocument(String nroDocument) {
        return customerMapper.toCustomerDto(customerRepositoryFacade.findByNroDocument(nroDocument.trim()));
    }

    @Override
    public ResponseListCustomerPaginationDto getCustomerPageable() {
        List<Customer> customers = customerRepositoryFacade.getAllCustomers();
        List<ResponseCustomerDTO> responseCustomers = customers.stream().map(customerMapper::toCustomerDto).collect(Collectors.toList());

        ResponseListCustomerPaginationDto responseListCustomerPaginationDto = new ResponseListCustomerPaginationDto();
        responseListCustomerPaginationDto.setCustomers(responseCustomers);
        responseListCustomerPaginationDto.setLimitMax(customers.size());
        return responseListCustomerPaginationDto;
    }



    @Override
    public ResponseListCustomerPaginationDto getCustomerPageable(Pageable pageable) {
        List<Customer> customers = customerRepositoryFacade.findAllPageable(Status.ACTIVO, pageable);
        List<ResponseCustomerDTO> responseCustomers = customers.stream().map(customerMapper::toCustomerDto).collect(Collectors.toList());

        ResponseListCustomerPaginationDto responseListCustomerPaginationDto = new ResponseListCustomerPaginationDto();
        responseListCustomerPaginationDto.setCustomers(responseCustomers);
        int totalData = customerRepositoryFacade.countByStatus(Status.ACTIVO);

        int limitMin = Util.getLimitPaginator(pageable, 1, (pageable.getPageNumber() * size) + 1);

        int limitMax = Util.getLimitPaginator(pageable, size, (pageable.getPageNumber() + 1) * size);

        responseListCustomerPaginationDto.setLimitMin(limitMin);
        responseListCustomerPaginationDto.setLimitMax(Math.min(totalData, limitMax));
        responseListCustomerPaginationDto.setTotalData(totalData);
        responseListCustomerPaginationDto.setSize(size);
        return responseListCustomerPaginationDto;
    }

    private void countingGeneralCustomer() {
        List<CountingGeneral> counting = countingGeneralRepositoryFacade.getAllCountingGeneral();

        if ((counting.isEmpty())) {
            CountingGeneral c = new CountingGeneral();

            c.setQuantity_of_customer(1);
            countingGeneralRepositoryFacade.saveCountingGeneral(c);

        } else {
            counting.forEach(p -> {
                CountingGeneral countingGeneral = countingGeneralRepositoryFacade.validateCountingGeneral(p.getId());

                countingGeneral.setQuantity_of_customer(p.getQuantity_of_customer() + 1);

                countingGeneralRepositoryFacade.saveCountingGeneral(countingGeneral);
            });
        }
    }


}
