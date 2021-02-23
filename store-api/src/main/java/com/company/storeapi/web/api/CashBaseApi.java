package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.response.finance.ResponseCashBase;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.model.payload.response.finance.ResponseListCashRegisterDailyPaginationDto;
import com.company.storeapi.services.finances.cashbase.CashBaseService;
import com.company.storeapi.services.finances.cashregister.CashRegisterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cash")
@CrossOrigin({"*"})
public class CashBaseApi {

    @Value("${spring.size.pagination}")
    private int size;

    private final CashBaseService cashBaseService;
    private final CashRegisterService cashRegisterService;

    public CashBaseApi(CashBaseService cashBaseService, CashRegisterService cashRegisterService) {
        this.cashBaseService = cashBaseService;
        this.cashRegisterService = cashRegisterService;
    }

    @GetMapping(value = "/cashRegisterDailyFilter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListCashRegisterDailyPaginationDto getAllCashRegisterDailyFilter() {
        return cashRegisterService.getCashRegisterPageable();
    }


    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListCashRegisterDailyPaginationDto getAllCashRegisterDailyPage(@Param(value = "page") int page) {
        Pageable requestedPage = PageRequest.of(page, size);
        return cashRegisterService.getCashRegisterPageable(requestedPage);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseCashBase findCashBaseByUltimate() {
        return cashBaseService.findCashBaseByUltimate();
    }

    @PostMapping(value = "/{cashBase}")
    public ResponseEntity<ResponseCashBase> create(@PathVariable double cashBase) {
        ResponseCashBase created = cashBaseService.save(cashBase);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/CashRegister")
    public ResponseEntity<ResponseCashRegisterDTO> cashRegister() {
        ResponseCashRegisterDTO created = cashRegisterService.saveCashRegister();
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }
}
