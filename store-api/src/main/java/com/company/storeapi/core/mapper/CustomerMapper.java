package com.company.storeapi.core.mapper;

import com.company.storeapi.model.dto.request.customer.RequestAddCustomerDTO;
import com.company.storeapi.model.dto.request.customer.RequestUpdateCustomerDTO;
import com.company.storeapi.model.dto.response.customer.ResponseCustomerDTO;
import com.company.storeapi.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    Customer toCustomer(RequestAddCustomerDTO requestAddCustomerDTO);

    Customer toCustomer(ResponseCustomerDTO requestAddCustomerDTO);

    ResponseCustomerDTO toCustomerDto(Customer customer);

    void updateCustomerFromDto(RequestUpdateCustomerDTO updateCustomerDto, @MappingTarget Customer customer);

}
