package com.company.storeapi.web.api;


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

     @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<ResponseProductDTO> getAllProduct() {
        return service.getAllProducts();
    }

    @GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseProductDTO> getProductByCategory(@PathVariable("id") String id) {
        return service.findProductByCategory(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseProductDTO> getProductById(@PathVariable("id") String id) {
        ResponseProductDTO entity = service.validateAndGetProductById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ResponseProductDTO> create(@RequestBody RequestAddProductDTO requestAddProductDTO) throws IOException {
        ResponseProductDTO created = service.saveProduct(requestAddProductDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseProductDTO> updateProduct(@PathVariable String id, @RequestBody RequestUpdateProductDTO productDTO) {
        ResponseProductDTO update = service.updateProduct(id, productDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }


    @PatchMapping(value = "/{id}/status/{status}")
    public ResponseEntity<ResponseProductDTO> updateStatus(@PathVariable String id, @PathVariable Status status) {
        ResponseProductDTO addUnit = service.updateStatus(id, status);
        return new ResponseEntity<>(addUnit, new HttpHeaders(), HttpStatus.OK);
    }


}
