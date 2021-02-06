package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.species.RequestAddSpeciesDTO;
import com.company.storeapi.model.payload.request.species.RequestUpdateSpeciesDTO;
import com.company.storeapi.model.payload.response.species.ResponseSpeciesDTO;
import com.company.storeapi.services.species.SpeciesService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/species")
@CrossOrigin({"*"})
public class SpeciesRestApi {

    private final SpeciesService service;

    public SpeciesRestApi(SpeciesService service) {
        this.service = service;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseSpeciesDTO> getAll()  {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSpeciesDTO> getById(@PathVariable("id") String id)
    {
        ResponseSpeciesDTO entity = service.validateAndGetById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSpeciesDTO> create(@RequestBody RequestAddSpeciesDTO requestAddDTO)  {
        ResponseSpeciesDTO created = service.save(requestAddDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSpeciesDTO> update (@RequestBody RequestUpdateSpeciesDTO requestUpdateDTO) {
        ResponseSpeciesDTO update = service.update(requestUpdateDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id)  {
        service.delete(id);
    }
}
