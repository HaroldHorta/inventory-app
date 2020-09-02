package com.company.storeapi.repositories.product;

import com.company.storeapi.model.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
