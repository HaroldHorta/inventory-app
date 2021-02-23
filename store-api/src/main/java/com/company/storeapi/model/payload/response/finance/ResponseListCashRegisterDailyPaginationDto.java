package com.company.storeapi.model.payload.response.finance;

import lombok.Data;

import java.util.List;

@Data
public class ResponseListCashRegisterDailyPaginationDto {

    private int limitMin;
    private int limitMax;
    private int totalData;
    private int size;
    private List<ResponseCashRegisterDTO> cashRegisters;
}
