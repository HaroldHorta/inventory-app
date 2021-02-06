package com.company.storeapi.services.finances.cashregister.impl;

import com.company.storeapi.core.mapper.CashRegisterMapper;
import com.company.storeapi.core.util.StandNameUtil;
import com.company.storeapi.model.entity.finance.CashBase;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.model.payload.response.finance.ResponseListCashRegisterDailyPaginationDto;
import com.company.storeapi.repositories.finances.cashBase.facade.CashBaseRepositoryFacade;
import com.company.storeapi.repositories.finances.cashregisterdaily.facade.CashRegisterDailyRepositoryFacade;
import com.company.storeapi.services.finances.cashregister.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.size.pagination}")
    private int size;

    @Override
    public ResponseCashRegisterDTO saveCashRegister() {

        CashRegisterDaily cashRegisterDaily = cashRegisterDailyRepositoryFacade.findCashRegisterDailyByUltimate();
        CashBase cashBase = cashBaseRepositoryFacade.findCashBaseByUltime();
        double totalSales = cashRegisterDaily.getDailyCashSales() + cashRegisterDaily.getDailyTransactionsSales() + cashRegisterDaily.getDailyCreditSales();

        cashRegisterDaily.setDailyCashBase(cashBase.getDailyCashBase());
        cashRegisterDaily.setTotalSales(totalSales);
        cashRegisterDaily.setCreateAt(new Date());
        cashRegisterDaily.setCashRegister(true);

        return cashRegisterMapper.dtoChasRegisterDocument( cashRegisterDailyRepositoryFacade.save(cashRegisterDaily));
    }

    @Override
    public ResponseListCashRegisterDailyPaginationDto getCashRegisterPageable() {
        List<CashRegisterDaily> cashRegisterDailies = cashRegisterDailyRepositoryFacade.findAllCashRegisterDaily();
        List<ResponseCashRegisterDTO> responseCashRegisters = cashRegisterDailies.stream().map(cashRegisterMapper::dtoChasRegisterDocument).collect(Collectors.toList());
        ResponseListCashRegisterDailyPaginationDto responseListCashRegisterDailyPaginationDto = new ResponseListCashRegisterDailyPaginationDto();
        responseListCashRegisterDailyPaginationDto.setLimitMax(cashRegisterDailies.size());
        responseListCashRegisterDailyPaginationDto.setCashRegisters(responseCashRegisters);
        return responseListCashRegisterDailyPaginationDto;
    }

    @Override
    public ResponseListCashRegisterDailyPaginationDto getCashRegisterPageable(Pageable pageable) {
        List<CashRegisterDaily> cashRegisterDailies = cashRegisterDailyRepositoryFacade.findAllByPageable(false, pageable);
        List<ResponseCashRegisterDTO> responseCashRegisters = cashRegisterDailies.stream().map(cashRegisterMapper::dtoChasRegisterDocument).collect(Collectors.toList());
        ResponseListCashRegisterDailyPaginationDto responseListCashRegisterDailyPaginationDto = new ResponseListCashRegisterDailyPaginationDto();

        int limitMin = getLimitCash(pageable, 1, (pageable.getPageNumber() * size) + 1);

        int limitMax = getLimitCash(pageable, size, (pageable.getPageNumber() + 1) * size);

        int totalData = cashRegisterDailyRepositoryFacade.countByPageable(false);
        responseListCashRegisterDailyPaginationDto.setCashRegisters(responseCashRegisters);
        responseListCashRegisterDailyPaginationDto.setLimitMin(limitMin);
        responseListCashRegisterDailyPaginationDto.setLimitMax(Math.min(totalData, limitMax));
        responseListCashRegisterDailyPaginationDto.setTotalData(totalData);
        responseListCashRegisterDailyPaginationDto.setSize(size);
        return responseListCashRegisterDailyPaginationDto;
    }

    private int getLimitCash(Pageable pageable, int i, int i2) {
        return StandNameUtil.getLimitPaginator(pageable, i, i2);
    }


}
