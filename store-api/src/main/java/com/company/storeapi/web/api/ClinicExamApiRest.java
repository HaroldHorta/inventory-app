package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.clinicaexam.RequestAddClinicExam;
import com.company.storeapi.model.payload.response.clinicexam.ResponseClinicExam;
import com.company.storeapi.services.clinicexam.ClinicExamService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/clinicExam")
@CrossOrigin({"*"})
public class ClinicExamApiRest {

    private final ClinicExamService clinicExamService;

    public ClinicExamApiRest(ClinicExamService clinicExamService) {
        this.clinicExamService = clinicExamService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseClinicExam> getAllClinicExam() {
        return clinicExamService.getAllClinicExam();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseClinicExam> getById(@PathVariable("id") String id) {
        ResponseClinicExam entity = clinicExamService.validateAndGetClinicExamById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseClinicExam> create(@RequestBody RequestAddClinicExam requestAddClinicExam) {
        ResponseClinicExam created = clinicExamService.saveClinicExam(requestAddClinicExam);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        clinicExamService.deleteClinicExam(id);
    }
}
