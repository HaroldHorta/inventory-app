package com.company.storeapi.services.finances.expenses.impl;

import com.company.storeapi.core.mapper.ExpensesMapper;
import com.company.storeapi.core.util.StandNameUtil;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.entity.finance.Expenses;
import com.company.storeapi.model.payload.request.finance.RequestAddExpensesDTO;
import com.company.storeapi.model.payload.response.finance.ResponseExpensesDTO;
import com.company.storeapi.model.payload.response.finance.ResponseListExpensesPaginationDto;
import com.company.storeapi.repositories.finances.cashRegisterDaily.facade.CashRegisterDailyRepositoryFacade;
import com.company.storeapi.repositories.finances.expenses.facade.ExpensesRepositoryFacade;
import com.company.storeapi.services.finances.expenses.ExpensesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
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

    @Value("${spring.size.pagination}")
    private int size;

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

    @Override
    public ResponseListExpensesPaginationDto getExpensesPageable() {
        List<Expenses> expenses = expensesRepositoryFacade.findAllExpenses();
        List<ResponseExpensesDTO> responseExpenses = expenses.stream().map(expensesMapper::DtoResponseExpenses).collect(Collectors.toList());
        ResponseListExpensesPaginationDto responseListExpensesPaginationDto = new ResponseListExpensesPaginationDto();
        responseListExpensesPaginationDto.setLimitMax(expenses.size());
        responseListExpensesPaginationDto.setExpenses(responseExpenses);
        return responseListExpensesPaginationDto;
    }

    @Override
    public ResponseListExpensesPaginationDto getExpensesPageable(Pageable pageable) {
        List<Expenses> expenses = expensesRepositoryFacade.findAllByPageable(false, pageable);
        List<ResponseExpensesDTO> responseExpenses = expenses.stream().map(expensesMapper::DtoResponseExpenses).collect(Collectors.toList());
        ResponseListExpensesPaginationDto responseListExpensesPaginationDto = new ResponseListExpensesPaginationDto();
        responseListExpensesPaginationDto.setExpenses(responseExpenses);
        int limitMin = getLimitExp(pageable, 1, (pageable.getPageNumber() * size) + 1);

        int limitMax = getLimitExp(pageable, size, (pageable.getPageNumber() + 1) * size);

        int totalData = expensesRepositoryFacade.countByPageable(false);
        responseListExpensesPaginationDto.setLimitMin(limitMin);
        responseListExpensesPaginationDto.setLimitMax(Math.min(totalData, limitMax));
        responseListExpensesPaginationDto.setTotalData(totalData);
        responseListExpensesPaginationDto.setSize(size);
        return responseListExpensesPaginationDto;
    }

    private int getLimitExp(Pageable pageable, int i, int i2) {
        return StandNameUtil.getLimitPaginator(pageable, i, i2);
    }



}
