package com.company.storeapi.services.product.impl;

import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.payload.request.product.FileUploadDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.services.product.FilesStorageService;
import org.springframework.stereotype.Service;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final ProductRepositoryFacade productRepositoryFacade;
    private final ProductMapper productMapper;

    public FilesStorageServiceImpl(ProductRepositoryFacade productRepositoryFacade, ProductMapper productMapper) {
        this.productRepositoryFacade = productRepositoryFacade;
        this.productMapper = productMapper;
    }

    @Override
    public ResponseProductDTO save(FileUploadDTO file) {

        Product product = productRepositoryFacade.validateAndGetProductById(file.getIdProduct());

        product.setPhoto(file.getDataPhoto());

        return productMapper.toProductDto(productRepositoryFacade.saveProduct(product));


    }
}
