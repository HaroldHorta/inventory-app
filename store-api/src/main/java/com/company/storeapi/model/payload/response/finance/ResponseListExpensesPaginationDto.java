package com.company.storeapi.model.payload.response.finance;

import lombok.Data;

import java.util.List;

@Data
public class ResponseListExpensesPaginationDto {

    private int count;
    private List<ResponseExpensesDTO> expenses;
}
