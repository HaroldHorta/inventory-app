package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.product.RequestUpdateUnitDTO;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.services.product.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/inventory")
@CrossOrigin({"*"})
public class InventoryRestApi {

    private final ProductService service;

    public InventoryRestApi(ProductService service) {
        this.service = service;
    }

    @PatchMapping(value="/unit")
    public ResponseEntity<ResponseProductDTO> addUnitProduct(@RequestBody RequestUpdateUnitDTO requestUpdateUnitDTO) {
        ResponseProductDTO addUnit = service.addUnitProduct(requestUpdateUnitDTO);
        return new ResponseEntity<>(addUnit, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/inventory", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseProductDTO> getAllProductInventory() {
        return service.getAllProductInventory();
    }

    @GetMapping("/{id}/unit/{unit}")
    public ResponseOrderProductItemsDTO details(@PathVariable String id, @PathVariable int unit) {
        return service.getItemsTotal(id, unit);
    }



}
