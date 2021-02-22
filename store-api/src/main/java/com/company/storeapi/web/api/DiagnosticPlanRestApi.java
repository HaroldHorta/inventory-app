package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.diagnosticplan.RequestDiagnosticPlan;
import com.company.storeapi.model.payload.response.diagnosticplan.ResponseDiagnosticPlan;
import com.company.storeapi.services.diagnosticplan.DiagnosticPlanService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/diagnosticPlan")
@CrossOrigin({"*"})
public class DiagnosticPlanRestApi {

    private final DiagnosticPlanService service;

    public DiagnosticPlanRestApi(DiagnosticPlanService service) {
        this.service = service;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseDiagnosticPlan> getAll()  {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDiagnosticPlan> getById(@PathVariable("id") String id)
    {
        ResponseDiagnosticPlan entity = service.validateAndGetById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDiagnosticPlan> create(@RequestBody RequestDiagnosticPlan requestAddDTO)  {
        ResponseDiagnosticPlan created = service.save(requestAddDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id)  {
        service.delete(id);
    }

}
