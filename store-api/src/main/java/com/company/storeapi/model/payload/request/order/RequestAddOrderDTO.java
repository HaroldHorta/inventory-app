package com.company.storeapi.model.payload.request.order;

import com.company.storeapi.model.payload.request.product.RequestOrderProductItemsDTO;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class RequestAddOrderDTO {

    private String id;
    @Valid
    private List<RequestOrderProductItemsDTO> products;

    private Double totalOrder;

}
