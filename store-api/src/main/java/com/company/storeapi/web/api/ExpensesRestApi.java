package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.payload.request.finance.RequestAddExpensesDTO;
import com.company.storeapi.model.payload.response.finance.ResponseExpensesDTO;
import com.company.storeapi.services.finances.expenses.ExpensesService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/expenses")
@CrossOrigin({"*"})
public class ExpensesRestApi {

    private final ExpensesService expensesService;

    public ExpensesRestApi(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseExpensesDTO> findAllExpenses() throws ServiceException {
        return expensesService.findAllExpenses();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseExpensesDTO> findExpensesById(@PathVariable("id") String  id)
    {
        ResponseExpensesDTO entity = expensesService.findExpensesById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseExpensesDTO> saveExpenses (@RequestBody RequestAddExpensesDTO requestAddExpensesDTO) throws ServiceException{
        ResponseExpensesDTO newExpenses = expensesService.saveExpenses(requestAddExpensesDTO);
        return new ResponseEntity<>(newExpenses, new HttpHeaders(), HttpStatus.CREATED);
    }


}
