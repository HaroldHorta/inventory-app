package com.company.storeapi.repositories.category;

import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category,String> {

    Boolean existsCategoryByDescription(String description);

    List<Category> findAllByStatus (Status status, Pageable pageable);
}
