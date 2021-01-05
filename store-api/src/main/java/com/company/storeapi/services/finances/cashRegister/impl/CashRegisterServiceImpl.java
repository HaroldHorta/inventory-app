package com.company.storeapi.services.finances.cashRegister.impl;

import com.company.storeapi.core.mapper.CashRegisterMapper;
import com.company.storeapi.model.entity.finance.CashBase;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.model.payload.response.finance.ResponseListCashRegisterDailyPaginationDto;
import com.company.storeapi.repositories.finances.cashBase.facade.CashBaseRepositoryFacade;
import com.company.storeapi.repositories.finances.cashRegisterDaily.facade.CashRegisterDailyRepositoryFacade;
import com.company.storeapi.services.finances.cashRegister.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashRegisterServiceImpl implements CashRegisterService {

    private final CashRegisterMapper cashRegisterMapper;
    private final CashRegisterDailyRepositoryFacade cashRegisterDailyRepositoryFacade;
    private final CashBaseRepositoryFacade cashBaseRepositoryFacade;

    @Override
    public ResponseCashRegisterDTO saveCashRegister() {

        CashRegisterDaily cashRegisterDaily = cashRegisterDailyRepositoryFacade.findCashRegisterDailyByUltimate();
        CashBase cashBase = cashBaseRepositoryFacade.findCashBaseByUltime();
        double totalSales = cashRegisterDaily.getDailyCashSales() + cashRegisterDaily.getDailyTransactionsSales() + cashRegisterDaily.getDailyCreditSales();

        cashRegisterDaily.setDailyCashBase(cashBase.getDailyCashBase());
        cashRegisterDaily.setTotalSales(totalSales);
        cashRegisterDaily.setCreateAt(new Date());
        cashRegisterDaily.setCashRegister(true);

        return cashRegisterMapper.DtoChasRegisterDocument( cashRegisterDailyRepositoryFacade.save(cashRegisterDaily));
    }

    @Override
    public ResponseListCashRegisterDailyPaginationDto getCashRegisterPageable() {
        List<CashRegisterDaily> cashRegisterDailies = cashRegisterDailyRepositoryFacade.findAllCashRegisterDaily();
        return getResponseListCashRegisterDailyPaginationDto(cashRegisterDailies);
    }

    @Override
    public ResponseListCashRegisterDailyPaginationDto getCashRegisterPageable(Pageable pageable) {
        List<CashRegisterDaily> cashRegisterDailies = cashRegisterDailyRepositoryFacade.findAllByPageable(false, pageable);
        return getResponseListCashRegisterDailyPaginationDto(cashRegisterDailies);
    }

    private ResponseListCashRegisterDailyPaginationDto getResponseListCashRegisterDailyPaginationDto(List<CashRegisterDaily> cashRegisterDailies) {
        List<ResponseCashRegisterDTO> responseCashRegisters = cashRegisterDailies.stream().map(cashRegisterMapper::DtoChasRegisterDocument).collect(Collectors.toList());
        ResponseListCashRegisterDailyPaginationDto responseListCashRegisterDailyPaginationDto = new ResponseListCashRegisterDailyPaginationDto();
        responseListCashRegisterDailyPaginationDto.setCount(cashRegisterDailies.size());
        responseListCashRegisterDailyPaginationDto.setCashRegisters(responseCashRegisters);
        return responseListCashRegisterDailyPaginationDto;
    }

}
