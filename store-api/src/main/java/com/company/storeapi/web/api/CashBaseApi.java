package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.payload.response.finance.ResponseCashBase;
import com.company.storeapi.services.finances.cashBase.CashBaseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cash")
@CrossOrigin({"*"})
public class CashBaseApi {

    private final CashBaseService cashBaseService;

    public CashBaseApi(CashBaseService cashBaseService) {
        this.cashBaseService = cashBaseService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseCashBase findCashBaseByUltime() throws ServiceException {
        return cashBaseService.findCashBaseByUltime();
    }

    @PostMapping(value ="/{cashBase}")
    public ResponseEntity<ResponseCashBase> create(@PathVariable Double cashBase) throws ServiceException {
        ResponseCashBase created = cashBaseService.save(cashBase);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }
}
