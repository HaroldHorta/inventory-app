package com.company.storeapi.repositories.category;

import com.company.storeapi.model.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category,String> {


    Category findCategoryById(String id);

    Category findCategoriesByDescription(String description);

    Boolean existsCategoryByDescription(String description);
}
