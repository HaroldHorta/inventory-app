package com.company.storeapi.services.product;

import com.company.storeapi.model.dto.request.product.RequestAddProductDTO;
import com.company.storeapi.model.dto.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.dto.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.dto.response.product.ResponseProductDTO;
import com.company.storeapi.model.enums.Status;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ResponseProductDTO> getAllProducts();

    ResponseProductDTO saveProduct(RequestAddProductDTO requestAddProductDTO, MultipartFile file);

    void deleteProduct(String id);

    ResponseProductDTO updateProduct(String id, RequestUpdateProductDTO requestUpdateCustomerDTO);

    ResponseProductDTO validateAndGetProductById(String id);

    ResponseOrderProductItemsDTO getItemsTotal(String id, int unit);

    ResponseProductDTO addUnitProduct(String id, int unit);

    ResponseProductDTO updateStatus(String id, Status status);

    }
