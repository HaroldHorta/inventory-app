package com.company.storeapi.services.product;

import com.company.storeapi.model.payload.request.product.RequestAddProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.model.enums.Status;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<ResponseProductDTO> getAllProducts();

    ResponseProductDTO saveProduct(RequestAddProductDTO requestAddProductDTO);

    ResponseProductDTO updateProduct(String id, RequestUpdateProductDTO requestUpdateCustomerDTO);

    ResponseProductDTO validateAndGetProductById(String id);

    ResponseOrderProductItemsDTO getItemsTotal(String id, int unit);

    ResponseProductDTO addUnitProduct(String id, int unit);

    ResponseProductDTO updateStatus(String id, Status status);

    List<ResponseProductDTO> findProductByCategory (String id);

    }
