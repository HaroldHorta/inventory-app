package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.product.RequestUpdateUnitDTO;
import com.company.storeapi.model.payload.response.product.ResponseListProductPaginationDto;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.services.product.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/inventory")
@CrossOrigin({"*"})
public class InventoryRestApi {

    @Value("${spring.size.pagination}")
    private int size;

    private final ProductService service;

    public InventoryRestApi(ProductService service) {
        this.service = service;
    }

    @PatchMapping(value="/unit")
    public ResponseEntity<ResponseProductDTO> addUnitProduct(@RequestBody RequestUpdateUnitDTO requestUpdateUnitDTO) {
        ResponseProductDTO addUnit = service.addUnitProduct(requestUpdateUnitDTO);
        return new ResponseEntity<>(addUnit, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListProductPaginationDto getAllProductFilters(@Param(value = "page") int page) {
        Pageable requestedPage = PageRequest.of(page, size);
        return service.getAllProductInventory(requestedPage);
    }

    @GetMapping("/{id}/unit/{unit}")
    public ResponseOrderProductItemsDTO details(@PathVariable String id, @PathVariable int unit) {
        return service.getItemsTotal(id, unit);
    }

    @GetMapping(value = "/allProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseListProductPaginationDto getAllProduct() {
        return service.getAllProductInventory();
    }


}
