package com.company.storeapi.repositories.product;

import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{'category.id':?0}")
    List<Product> findProductByCategory_Description(String id);
    List<Product> findAllByStatus (Status status, Pageable pageable);
}
