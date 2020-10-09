package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.services.product.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/inventory")
@CrossOrigin({"*"})
public class InventoryRestApi {

    private final ProductService service;

    public InventoryRestApi(ProductService service) {
        this.service = service;
    }

    @PatchMapping(value="/{id}/unit/{unit}")
    public ResponseEntity<ResponseProductDTO> addUnitProduct(@PathVariable String id, @PathVariable int unit) throws ServiceException {
        ResponseProductDTO addUnit = service.addUnitProduct(id,unit);
        return new ResponseEntity<>(addUnit, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}/unit/{unit}")
    public ResponseOrderProductItemsDTO details(@PathVariable String id, @PathVariable int unit) throws ServiceException {
        return service.getItemsTotal(id, unit);
    }
}
