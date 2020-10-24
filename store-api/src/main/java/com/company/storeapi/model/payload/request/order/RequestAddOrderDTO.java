package com.company.storeapi.model.payload.request.order;

import com.company.storeapi.model.payload.request.product.RequestOrderProductItemsDTO;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RequestAddOrderDTO {

    @Valid
    private List<RequestOrderProductItemsDTO> products;



}
