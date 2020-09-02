package com.company.storeapi.repositories.product.facade;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.entity.Product;

import java.util.List;

public interface ProductRepositoryFacade {

    List<Product> getAllProduct();

    Product saveProduct(Product product);

    void deleteProduct(String id) throws ServiceException;

    Product validateAndGetProductById(String id);
}
