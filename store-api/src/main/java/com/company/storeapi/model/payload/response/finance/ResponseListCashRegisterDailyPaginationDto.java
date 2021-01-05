package com.company.storeapi.model.payload.response.finance;

import lombok.Data;

import java.util.List;

@Data
public class ResponseListCashRegisterDailyPaginationDto {

    private int count;
    private List<ResponseCashRegisterDTO> cashRegisters;
}
