package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.payload.response.finance.ResponseCashBase;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import com.company.storeapi.services.finances.cashBase.CashBaseService;
import com.company.storeapi.services.finances.cashRegister.CashRegisterService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cash")
@CrossOrigin({"*"})
public class CashBaseApi {

    private final CashBaseService cashBaseService;
    private final CashRegisterService cashRegisterService;

    public CashBaseApi(CashBaseService cashBaseService, CashRegisterService cashRegisterService) {
        this.cashBaseService = cashBaseService;
        this.cashRegisterService = cashRegisterService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseCashBase findCashBaseByUltimate() throws ServiceException {
        return cashBaseService.findCashBaseByUltimate();
    }

    @PostMapping(value ="/{cashBase}")
    public ResponseEntity<ResponseCashBase> create(@PathVariable double cashBase) throws ServiceException {
        ResponseCashBase created = cashBaseService.save(cashBase);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value ="/CashRegister")
    public ResponseEntity<ResponseCashRegisterDTO> cashRegister() throws ServiceException {
        ResponseCashRegisterDTO created = cashRegisterService.saveCashRegister();
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }
}
