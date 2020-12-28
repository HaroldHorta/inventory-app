package com.company.storeapi.core.mapper;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.customer.RequestAddCustomerDTO;
import com.company.storeapi.model.payload.request.customer.RequestUpdateCustomerDTO;
import com.company.storeapi.model.payload.response.customer.ResponseCustomerDTO;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.repositories.customer.facade.CustomerRepositoryFacade;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CustomerMapper {

    @Autowired
    private CustomerRepositoryFacade customerRepositoryFacade;

    Pattern pattern = Pattern
            .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public Customer toCustomer(RequestAddCustomerDTO requestAddCustomerDTO){

        Boolean existDocument = customerRepositoryFacade.validateAndGetCustomerByNroDocument(requestAddCustomerDTO.getNroDocument().trim());
        if(existDocument){
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"El numero de cedula ya existe");
        }

        Boolean existEmail = customerRepositoryFacade.validateAndGetCustomerByEmail(requestAddCustomerDTO.getEmail().trim());

        if(existEmail){
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"El correo ya existe");
        }
        Customer customer = new Customer();
        if(!requestAddCustomerDTO.getName().isEmpty() && !requestAddCustomerDTO.getTypeDocument().toString().isEmpty() && !requestAddCustomerDTO.getNroDocument().isEmpty() ) {
            customer.setName(requestAddCustomerDTO.getName());
            customer.setTypeDocument(requestAddCustomerDTO.getTypeDocument());
            customer.setNroDocument(requestAddCustomerDTO.getNroDocument().trim());
            Matcher mather = pattern.matcher(requestAddCustomerDTO.getEmail().trim());
            if (mather.find()) {
                customer.setEmail(requestAddCustomerDTO.getEmail().trim());
            } else {
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"Email no valido");
            }
            customer.setAddress(requestAddCustomerDTO.getAddress());
            customer.setPhone(requestAddCustomerDTO.getPhone().trim());
            customer.setStatus(Status.ACTIVE);
            return customer;
        } else  {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"Los campos nombre, tipo de documento y numero de documento son obligatorios");
        }

    }

    public abstract Customer toCustomer(ResponseCustomerDTO requestAddCustomerDTO);

    public abstract ResponseCustomerDTO toCustomerDto(Customer customer);

    public abstract void updateCustomerFromDto(RequestUpdateCustomerDTO updateCustomerDto, @MappingTarget Customer customer);

}
