package com.company.storeapi.repositories.product.facade;

import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductRepositoryFacade {

    List<Product> getAllProduct (Status status, Pageable pageable);

    Product saveProduct(Product product);

    Product validateAndGetProductById(String id);

    List<Product> findProductByCategory(String id);


}
