package com.company.storeapi.repositories.category.facade;

import com.company.storeapi.model.entity.Category;

import java.util.List;

/**
 * The interface Category repository facade.
 */
public interface CategoryRepositoryFacade {

    /**
     * Find all category list.
     *
     * @return the list
     * @ the service exception
     */
    List<Category> getAllCategory() ;


    /**
     * Find category by id category.
     *
     * @param id the id
     * @return the category
     * @ the service exception
     */
    Category validateAndGetCategoryById(String id);

    /**
     * Create category category.
     *
     * @param entity the entity
     * @return the category
     * @ the service exception
     */
    Category saveCategory(Category entity) ;

    /**
     * Delete category.
     *
     * @param id the id
     * @ the service exception
     */
    void deleteCategory(String  id) ;

    Boolean existsCategoryByDescription(String description);

}
