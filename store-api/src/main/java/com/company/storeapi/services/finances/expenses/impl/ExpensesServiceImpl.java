package com.company.storeapi.services.finances.expenses.impl;

import com.company.storeapi.core.mapper.ExpensesMapper;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.entity.finance.Expenses;
import com.company.storeapi.model.payload.request.finance.RequestAddExpensesDTO;
import com.company.storeapi.model.payload.response.finance.ResponseExpensesDTO;
import com.company.storeapi.repositories.finances.cashRegisterDaily.facade.CashRegisterDailyRepositoryFacade;
import com.company.storeapi.repositories.finances.expenses.facade.ExpensesRepositoryFacade;
import com.company.storeapi.services.finances.expenses.ExpensesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpensesServiceImpl implements ExpensesService {

    private final ExpensesRepositoryFacade expensesRepositoryFacade;
    private final ExpensesMapper expensesMapper;
    private final CashRegisterDailyRepositoryFacade cashRegisterDailyRepositoryFacade;

    @Override
    public List<ResponseExpensesDTO> findAllExpenses() {
        List<Expenses> expenses = expensesRepositoryFacade.findAllExpenses();
        return expenses.stream().map(expensesMapper::DtoResponseExpenses).collect(Collectors.toList());
    }

    @Override
    public ResponseExpensesDTO saveExpenses(RequestAddExpensesDTO requestAddExpensesDTO) {


        Expenses expenses = new Expenses();
        expenses.setDescription(requestAddExpensesDTO.getDescription());
        expenses.setPrice(requestAddExpensesDTO.getPrice());
        expenses.setImages(requestAddExpensesDTO.getImages());
        expenses.setCreateAt(new Date());

        if(!(cashRegisterDailyRepositoryFacade.existsCashRegisterDailiesByCashRegister(false))){

            CashRegisterDaily cashRegisterDaily = new CashRegisterDaily();
            cashRegisterDaily.setCreateAt(new Date());
            cashRegisterDailyRepositoryFacade.save(cashRegisterDaily);

        }


        CashRegisterDaily cashRegisterDaily = cashRegisterDailyRepositoryFacade.findCashRegisterDailyByUltimate();
        cashRegisterDaily.setMoneyOut(cashRegisterDaily.getMoneyOut()+requestAddExpensesDTO.getPrice());

        cashRegisterDailyRepositoryFacade.save(cashRegisterDaily);


        return expensesMapper.DtoResponseExpenses(expensesRepositoryFacade.saveExpenses(expenses));
    }

    @Override
    public ResponseExpensesDTO findExpensesById(String id) {
        return expensesMapper.DtoResponseExpenses(expensesRepositoryFacade.findExpensesById(id));
    }
}
