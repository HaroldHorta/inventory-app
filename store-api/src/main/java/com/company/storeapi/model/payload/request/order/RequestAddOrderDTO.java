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

    @Schema(example = "5f4d93a00d70a908c47d3044")
    @NotNull
    private String customerId;

    @Schema(example = "CASH")
    @NotNull
    private PaymentType paymentType;

    @Valid
    private List<RequestOrderProductItemsDTO> products;



}
