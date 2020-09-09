package com.company.storeapi.services.product.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.core.util.DateUtil;
import com.company.storeapi.model.dto.request.product.RequestAddProductDTO;
import com.company.storeapi.model.dto.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.dto.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.dto.response.product.ResponseProductDTO;
import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.repositories.category.facade.CategoryRepositoryFacade;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepositoryFacade productRepositoryFacade;
    private final CategoryRepositoryFacade categoryRepositoryFacade;
    private final ProductMapper productMapper;

    @Override
    public List<ResponseProductDTO> getAllProducts() {
        List<Product> products = productRepositoryFacade.getAllProduct();
        return products.stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    @Override
    public ResponseProductDTO saveProduct(RequestAddProductDTO requestAddProductDTO) {
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
        product.setUpdateAt(DateUtil.getDateActual());
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
       if(unit>0){
           orderProduct.setId(id);
           orderProduct.setUnit(unit);
           orderProduct.setTotal(prod.getPriceSell()*unit);
       }else{
           throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATO_CORRUPTO,"la cantidad a ingresar no puede ser 0 o menor a 0");
       }
        return orderProduct;
    }

    @Override
    public ResponseProductDTO addUnitProduct(String id, int unit) {
        Product product = productRepositoryFacade.validateAndGetProductById(id);
        if(unit>0){
            int unitNew = product.getUnit()+unit;
            product.setUnit(unitNew);
        }else{
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATO_CORRUPTO,"la cantidad a ingresar no puede ser 0 o menor a 0");
        }
        return productMapper.toProductDto(productRepositoryFacade.saveProduct(product));

    }
}
