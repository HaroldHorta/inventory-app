package com.company.storeapi.services.product;

import com.company.storeapi.model.dto.request.product.RequestAddProductDTO;
import com.company.storeapi.model.dto.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.dto.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.dto.response.product.ResponseProductDTO;

import java.util.List;

public interface ProductService {

    List<ResponseProductDTO> getAllProducts();

    ResponseProductDTO saveProduct(RequestAddProductDTO requestAddProductDTO);

    void deleteProduct(String id);

    ResponseProductDTO updateProduct(String id, RequestUpdateProductDTO requestUpdateCustomerDTO);

    ResponseProductDTO validateAndGetProductById(String id);

    ResponseOrderProductItemsDTO getItemsTotal(String id, int unit);

    ResponseProductDTO addUnitProduct(String id, int unit);

    }
