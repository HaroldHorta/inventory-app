package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.breed.RequestAddBreedDTO;
import com.company.storeapi.model.payload.request.breed.RequestUpdateBreedDTO;
import com.company.storeapi.model.payload.response.breed.ResponseBreedDTO;
import com.company.storeapi.model.payload.response.breed.ResponseListBreedPaginationDto;
import com.company.storeapi.services.breed.BreedService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/breed")
@CrossOrigin({"*"})
public class BreedRestApi {


    @Value("${spring.size.pagination}")
    private int size;

    private final BreedService service;

    public BreedRestApi(BreedService service) {
        this.service = service;
    }

    @GetMapping(value = "/breedFilter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListBreedPaginationDto getAllBreedFilter() {
        return service.getBreedPageable();
    }


    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListBreedPaginationDto getAllBreedPage(@Param(value = "page") int page) {
        Pageable requestedPage = PageRequest.of(page, size);
        return service.getBreedPageable(requestedPage);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseBreedDTO> getAllBreed()  {
        return service.getAllBreed();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBreedDTO> getBreedById(@PathVariable("id") String id)
    {
        ResponseBreedDTO entity = service.validateAndGetBreedById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBreedDTO> create(@RequestBody RequestAddBreedDTO requestAddBreedDTO)  {
        ResponseBreedDTO created = service.saveBreed(requestAddBreedDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBreedDTO> update (@RequestBody RequestUpdateBreedDTO requestUpdateBreedDTO) {
        ResponseBreedDTO update = service.updateBreed(requestUpdateBreedDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id)  {
        service.deleteById(id);
    }
}
