package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.payload.request.order.RequestAddOrderDTO;
import com.company.storeapi.model.payload.request.order.RequestUpdateOrderDTO;
import com.company.storeapi.model.payload.request.product.RequestOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.order.ResponseOrderDTO;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.services.customer.CustomerService;
import com.company.storeapi.services.product.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CustomerService.class, ProductService.class}
)

public interface OrderMapper {

    @Mapping(source = "products", target = "products")
    ResponseOrderDTO toOrderDto(Order order);

    RequestAddOrderDTO toRequestAdd(RequestUpdateOrderDTO requestUpdateOrderDTO);

    Set<ResponseOrderProductItemsDTO> responseOrderProductItemsDTO(List<RequestOrderProductItemsDTO> order);

}
