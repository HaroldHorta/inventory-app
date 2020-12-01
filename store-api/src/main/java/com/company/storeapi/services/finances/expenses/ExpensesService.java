package com.company.storeapi.services.finances.expenses;

import com.company.storeapi.model.payload.request.finance.RequestAddExpensesDTO;
import com.company.storeapi.model.payload.response.finance.ResponseExpensesDTO;

import java.util.List;

public interface ExpensesService {

    List<ResponseExpensesDTO> findAllExpenses();

    ResponseExpensesDTO saveExpenses (RequestAddExpensesDTO requestAddExpensesDTO);

    ResponseExpensesDTO findExpensesById (String id);
}
