package com.company.storeapi.services.product.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.model.dto.request.product.RequestAddProductDTO;
import com.company.storeapi.model.dto.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.dto.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.dto.response.product.ResponseProductDTO;
import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.repositories.category.facade.CategoryRepositoryFacade;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepositoryFacade productRepositoryFacade;
    private final CategoryRepositoryFacade categoryRepositoryFacade;
    private final ProductMapper productMapper;

    @Value("${config.uploads.path}")
    private String path;

    private final HttpServletRequest request;

    @Override
    public List<ResponseProductDTO> getAllProducts() {
        List<Product> products = productRepositoryFacade.getAllProduct();
        return products.stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    @Override
    public ResponseProductDTO saveProduct(RequestAddProductDTO requestAddProductDTO, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String uploadsDir = path;
                String realPathToUploads = request.getServletContext().getRealPath(uploadsDir);
                if (!new File(realPathToUploads).exists()) {
                    new File(realPathToUploads);
                }

                String orgName = file.getOriginalFilename();
                String filePath = realPathToUploads + orgName;

                requestAddProductDTO.setPhoto(filePath);
                File dest = new File(filePath);
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return productMapper.toProductDto(productRepositoryFacade.saveProduct(productMapper.toProduct(requestAddProductDTO)));
    }
            @Override
    public void deleteProduct(String id) {
        productRepositoryFacade.deleteProduct(id);
    }

    @Override
    public ResponseProductDTO updateProduct(String id, RequestUpdateProductDTO requestUpdateCustomerDTO) {
        Product product = productRepositoryFacade.validateAndGetProductById(id);
        Category category = categoryRepositoryFacade.validateAndGetCategoryById(requestUpdateCustomerDTO.getCategoryId());
        product.setCategory(category);
        product.setUnit(requestUpdateCustomerDTO.getUnit());
        product.setUpdateAt(new Date());
        productMapper.updateProductFromDto(requestUpdateCustomerDTO, product);
        return productMapper.toProductDto(productRepositoryFacade.saveProduct(product));
    }

    @Override
    public ResponseProductDTO validateAndGetProductById(String id) {
        return productMapper.toProductDto(productRepositoryFacade.validateAndGetProductById(id));
    }

    @Override
    public ResponseOrderProductItemsDTO getItemsTotal(String id, int unit) {
        Product prod = productRepositoryFacade.validateAndGetProductById(id);
        ResponseOrderProductItemsDTO orderProduct = new ResponseOrderProductItemsDTO();
       if(unit<=0){
           throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATO_CORRUPTO,"la cantidad a ingresar no puede ser 0 o menor a 0");
       }else if(unit>prod.getUnit()){
           throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATO_CORRUPTO,"la cantidad de " + prod.getName() + " es mayor a la cantidad del presente en el inventario");
       }else{
           orderProduct.setId(id);
           orderProduct.setUnit(unit);
           orderProduct.setTotal(prod.getPriceSell()*unit);
       }
        return orderProduct;
    }

    @Override
    public ResponseProductDTO addUnitProduct(String id, int unit) {
        Product product = productRepositoryFacade.validateAndGetProductById(id);
        if(unit>0){
            int unitNew = product.getUnit()+unit;
            product.setStatus(Status.ACTIVE);
            product.setUnit(unitNew);
        }else{
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATO_CORRUPTO,"la cantidad a ingresar no puede ser 0 o menor a 0");
        }
        return productMapper.toProductDto(productRepositoryFacade.saveProduct(product));

    }

    @Override
    public ResponseProductDTO updateStatus(String id, Status status) {
        Product product = productRepositoryFacade.validateAndGetProductById(id);
        product.setStatus(status);
        return productMapper.toProductDto(productRepositoryFacade.saveProduct(product));

    }
}
