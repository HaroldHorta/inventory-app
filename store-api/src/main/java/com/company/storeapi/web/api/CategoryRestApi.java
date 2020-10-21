package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.payload.request.category.RequestAddCategoryDTO;
import com.company.storeapi.model.payload.request.category.RequestUpdateCategoryDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.services.category.CategoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Category rest api.
 */
@RestController
@RequestMapping(value = "/api/category")
@CrossOrigin({"*"})
public class CategoryRestApi {

    private final CategoryService service;

    /**
     * Instantiates a new Category rest api.
     *
     * @param service the service
     */
    public CategoryRestApi(CategoryService service) {
        this.service = service;
    }


    /**
     * Gets all category.
     *
     * @return the all category
     * @throws ServiceException the service exception
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseCategoryDTO> getAllCategory() throws ServiceException {
        return service.getAllCategory();
    }

    /**
     * Gets category by id.
     *
     * @param id the id
     * @return the category by id
     * @throws ServiceException the service exception
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCategoryDTO> getCategoryById(@PathVariable("id") String id)
            throws ServiceException {
        ResponseCategoryDTO entity = service.validateAndGetCategoryById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Create response entity.
     *
     * @param requestAddCategoryDTO the request add category dto
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCategoryDTO> create(@RequestBody RequestAddCategoryDTO requestAddCategoryDTO) throws ServiceException {
        ResponseCategoryDTO created = service.saveCategory(requestAddCategoryDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Update response entity.
     *
     * @param requestUpdateCategoryDTO the request add category dto
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCategoryDTO> update (@RequestBody RequestUpdateCategoryDTO requestUpdateCategoryDTO) throws ServiceException{
        ResponseCategoryDTO update = service.updateCategory(requestUpdateCategoryDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     * @throws ServiceException the service exception
     */
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) throws ServiceException {
        service.deleteById(id);
    }

}
