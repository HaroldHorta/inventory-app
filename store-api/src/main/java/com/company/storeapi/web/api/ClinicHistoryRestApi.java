package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.clinichistory.RequestAddClinicHistoryDTO;
import com.company.storeapi.model.payload.request.clinichistory.RequestUpdateClinicHistoryDTO;
import com.company.storeapi.model.payload.response.clinichistory.ResponseClinicHistoryDTO;
import com.company.storeapi.services.clinichistory.ClinicHistoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/clinic-history")
@CrossOrigin({"*"})
public class ClinicHistoryRestApi {

    private final ClinicHistoryService service;

    public ClinicHistoryRestApi(ClinicHistoryService service) {
        this.service = service;
    }


    @GetMapping(value = "customer/{nroDocument}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseClinicHistoryDTO> getByCustomer(@PathVariable("nroDocument") String nroDocument)  {
        return service.getClinicHistoryByCustomerNroDocument(nroDocument);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseClinicHistoryDTO> getById(@PathVariable("id") String id)
    {
        ResponseClinicHistoryDTO entity = service.validateAndGetClinicHistoryById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseClinicHistoryDTO> create(@RequestBody RequestAddClinicHistoryDTO requestAddClinicHistoryDTO)  {
        ResponseClinicHistoryDTO created = service.saveClinicHistory(requestAddClinicHistoryDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseClinicHistoryDTO> update (@RequestBody RequestUpdateClinicHistoryDTO requestUpdateClinicHistoryDTO) {
        ResponseClinicHistoryDTO update = service.updateClinicHistory(requestUpdateClinicHistoryDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }
}
