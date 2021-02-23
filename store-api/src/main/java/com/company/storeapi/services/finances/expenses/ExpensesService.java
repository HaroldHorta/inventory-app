package com.company.storeapi.services.finances.expenses;

import com.company.storeapi.model.payload.request.finance.RequestAddExpensesDTO;
import com.company.storeapi.model.payload.response.finance.ResponseExpensesDTO;
import com.company.storeapi.model.payload.response.finance.ResponseListExpensesPaginationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpensesService {

    List<ResponseExpensesDTO> findAllExpenses();

    ResponseExpensesDTO saveExpenses (RequestAddExpensesDTO requestAddExpensesDTO);

    ResponseExpensesDTO findExpensesById (String id);

    ResponseListExpensesPaginationDto getExpensesPageable();

    ResponseListExpensesPaginationDto getExpensesPageable(Pageable pageable);
}
