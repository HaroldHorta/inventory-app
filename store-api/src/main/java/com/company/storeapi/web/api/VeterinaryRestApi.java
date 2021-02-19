package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.veterinary.RequestAddVeterinaryDTO;
import com.company.storeapi.model.payload.request.veterinary.RequestUpdateVeterinaryDTO;
import com.company.storeapi.model.payload.response.veterinary.ResponseVeterinaryDTO;
import com.company.storeapi.services.veterinary.VeterinaryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/veterinary")
@CrossOrigin({"*"})
public class VeterinaryRestApi {

    private final VeterinaryService service;

    public VeterinaryRestApi(VeterinaryService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseVeterinaryDTO> getAllVeterinary() {
        return service.getAllVeterinary();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVeterinaryDTO> getById(@PathVariable("id") String id) {
        ResponseVeterinaryDTO entity = service.validateAndGetVeterinaryById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/professionalCard/{professionalCard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVeterinaryDTO> getByProfessionalCard(@PathVariable("professionalCard") String professionalCard) {
        ResponseVeterinaryDTO entity = service.validateAndGetVeterinaryByProfessionalCard(professionalCard);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVeterinaryDTO> create(@RequestBody RequestAddVeterinaryDTO requestAddVeterinaryDTO) {
        ResponseVeterinaryDTO created = service.saveVeterinary(requestAddVeterinaryDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVeterinaryDTO> update(@RequestBody RequestUpdateVeterinaryDTO requestUpdateVeterinaryDTO) {
        ResponseVeterinaryDTO update = service.updateVeterinary(requestUpdateVeterinaryDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        service.deleteVeterinary(id);
    }
}
