package com.company.storeapi.core.mapper;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.customer.RequestAddCustomerDTO;
import com.company.storeapi.model.payload.request.customer.RequestUpdateCustomerDTO;
import com.company.storeapi.model.payload.response.customer.ResponseCustomerDTO;
import com.company.storeapi.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CustomerMapper {

    Pattern pattern = Pattern
            .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public Customer toCustomer(RequestAddCustomerDTO requestAddCustomerDTO){
        Customer customer = new Customer();
        if(!requestAddCustomerDTO.getName().isEmpty() && !requestAddCustomerDTO.getTypeDocument().toString().isEmpty() && !requestAddCustomerDTO.getNroDocument().isEmpty() ) {
            customer.setName(requestAddCustomerDTO.getName());
            customer.setTypeDocument(requestAddCustomerDTO.getTypeDocument());
            customer.setNroDocument(requestAddCustomerDTO.getNroDocument());
            Matcher mather = pattern.matcher(requestAddCustomerDTO.getEmail());
            if (mather.find()) {
                customer.setEmail(requestAddCustomerDTO.getEmail());
            } else {
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"Email no valido");
            }
            customer.setAddress(requestAddCustomerDTO.getAddress());
            customer.setPhone(requestAddCustomerDTO.getPhone());
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
