package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.payload.request.category.RequestAddCategoryDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.services.category.CategoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/category")
@CrossOrigin({"*"})
public class CategoryRestApi {

    private final CategoryService service;

    public CategoryRestApi(CategoryService service) {
        this.service = service;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('SELLER')")
    public List<ResponseCategoryDTO> getAllCategory() throws ServiceException {
        return service.getAllCategory();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCategoryDTO> create(@RequestBody RequestAddCategoryDTO requestAddCategoryDTO) throws ServiceException {
        ResponseCategoryDTO created = service.saveCategory(requestAddCategoryDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
   // @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ResponseCategoryDTO> getCategoryById(@PathVariable("id") String id)
            throws ServiceException {
        ResponseCategoryDTO entity = service.validateAndGetCategoryById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

   @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) throws ServiceException{
      service.deleteById(id);
    }

}
