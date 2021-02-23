package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.vaccination.RequestAddVaccinationDTO;
import com.company.storeapi.model.payload.request.vaccination.RequestUpdateVaccinationDTO;
import com.company.storeapi.model.payload.response.vaccination.ResponseVaccinationDTO;
import com.company.storeapi.services.vaccination.VaccinationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/vaccination")
@CrossOrigin({"*"})
public class VaccinationRestApi {

    private final VaccinationService service;

    public VaccinationRestApi(VaccinationService service) {
        this.service = service;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseVaccinationDTO> getAll()  {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVaccinationDTO> getById(@PathVariable("id") String id)
    {
        ResponseVaccinationDTO entity = service.validateAndGetById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVaccinationDTO> create(@RequestBody RequestAddVaccinationDTO requestAddDTO)  {
        ResponseVaccinationDTO created = service.save(requestAddDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVaccinationDTO> update (@RequestBody RequestUpdateVaccinationDTO requestUpdateDTO) {
        ResponseVaccinationDTO update = service.update(requestUpdateDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id)  {
        service.delete(id);
    }
}
