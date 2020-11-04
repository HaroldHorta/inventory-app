package com.company.storeapi.web.api;


import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.product.RequestAddProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.services.product.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
@CrossOrigin({"*"})
public class ProductRestApi {

    private final ProductService service;

    public ProductRestApi(ProductService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseProductDTO> getAllProduct() throws ServiceException {
        return service.getAllProducts();
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseProductDTO> getAllProductFilters() throws ServiceException {
        return service.getAllProductsFilters();
    }

   @GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseProductDTO> getProductByCategory(@PathVariable("id") String id) throws ServiceException {
        return service.findProductByCategory(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseProductDTO> getProductById(@PathVariable("id") String  id)
            throws ServiceException {
        ResponseProductDTO entity = service.validateAndGetProductById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping()
       public ResponseEntity<ResponseProductDTO> create(@RequestBody RequestAddProductDTO requestAddProductDTO) throws ServiceException {
        ResponseProductDTO created = service.saveProduct(requestAddProductDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseProductDTO> updateProduct (@PathVariable String id, @RequestBody RequestUpdateProductDTO productDTO) throws ServiceException{
        ResponseProductDTO update = service.updateProduct(id, productDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseProductDTO> updateProductWithPhoto (@PathVariable String id, @RequestBody RequestUpdateProductDTO productDTO,  @RequestParam("file") MultipartFile file) throws ServiceException, IOException {
        ResponseProductDTO update = service.updateProductWithImages(id, productDTO, file);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value="/{id}/status/{status}")
    public ResponseEntity<ResponseProductDTO> updateStatus(@PathVariable String id, @PathVariable Status status) throws ServiceException{
        ResponseProductDTO addUnit = service.updateStatus(id,status);
        return new ResponseEntity<>(addUnit, new HttpHeaders(), HttpStatus.OK);
    }


}
