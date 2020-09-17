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
public class RequestUpdateOrderDTO {

    @Schema(example = "5f499baf94406c0fdcaf87f3")
    @NotNull
    private String customerId;

    @Schema(example = "CASH")
    @NotNull
    private PaymentType paymentType;

    @Schema(example = "OPEN")
    @NotNull
    private OrderStatus orderStatus;

    @Valid
    private List<RequestOrderProductItemsDTO> products;
}
