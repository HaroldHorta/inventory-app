package com.company.storeapi.services.product.impl;

import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.payload.request.user.FileInfo;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.services.product.FilesStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final ProductRepositoryFacade productRepositoryFacade;
    private final ProductMapper productMapper;

    public FilesStorageServiceImpl(ProductRepositoryFacade productRepositoryFacade, ProductMapper productMapper) {
        this.productRepositoryFacade = productRepositoryFacade;
        this.productMapper = productMapper;
    }

    @Override
    public ResponseProductDTO save(String id, MultipartFile file) {
        try {
            Product product = productRepositoryFacade.validateAndGetProductById(id);
           String namePhoto = UUID.randomUUID().toString() + "-" + Objects.requireNonNull(file.getOriginalFilename())
                    .replace(" ", "")
                    .replace(":", "")
                    .replace("\\", "");

           String fileName = StringUtils.cleanPath(namePhoto);

            FileInfo fileInfo = new FileInfo(fileName, file.getContentType(), file.getBytes());
          product.setPhoto(fileInfo);

       return productMapper.toProductDto(productRepositoryFacade.saveProduct(product));

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
