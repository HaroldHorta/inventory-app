package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.pet.RequestAddPetDTO;
import com.company.storeapi.model.payload.request.pet.RequestUpdatePetDTO;
import com.company.storeapi.model.payload.response.pet.ResponsePetDTO;
import com.company.storeapi.services.pet.PetService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/pet")
@CrossOrigin({"*"})
public class PetRestApi {

    private final PetService service;

    public PetRestApi(PetService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponsePetDTO> getAllPet()  {
        return service.getAllPet();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> getById(@PathVariable("id") String id)
    {
        ResponsePetDTO entity = service.validateAndGetPetById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> create(@RequestBody RequestAddPetDTO requestAddPetDTO)  {
        ResponsePetDTO created = service.savePet(requestAddPetDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> update (@RequestBody RequestUpdatePetDTO requestUpdatePetDTO) {
        ResponsePetDTO update = service.updatePet(requestUpdatePetDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id)  {
        service.deletePet(id);
    }
}
