package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.entity.CountingGeneral;
import com.company.storeapi.services.countingGeneral.CountingGeneralService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/report")
@CrossOrigin({"*"})
public class CountingGeneralRestApi {

    private final CountingGeneralService countingGeneralService;

    public CountingGeneralRestApi(CountingGeneralService countingGeneralService) {
        this.countingGeneralService = countingGeneralService;
    }

    @GetMapping(value = "/countingGeneral")
    public List<CountingGeneral> getCountingGeneral() throws ServiceException{
       return countingGeneralService.getAllCountingGeneral();
    }


}
