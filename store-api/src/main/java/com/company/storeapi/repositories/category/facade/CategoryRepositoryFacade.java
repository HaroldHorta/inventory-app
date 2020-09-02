package com.company.storeapi.repositories.category.facade;

import com.company.storeapi.core.exceptions.base.ServiceException;
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
     * @throws ServiceException the service exception
     */
    List<Category> getAllCategory() throws ServiceException;


    /**
     * Find category by id category.
     *
     * @param id the id
     * @return the category
     * @throws ServiceException the service exception
     */
    Category validateAndGetCategoryById(String id);

    /**
     * Find category by description category.
     *
     * @param description the description
     * @return the category
     * @throws ServiceException the service exception
     */
    Category findCategoryByDescription(String description) throws ServiceException;

    /**
     * Create category category.
     *
     * @param entity the entity
     * @return the category
     * @throws ServiceException the service exception
     */
    Category saveCategory(Category entity) throws ServiceException;

    /**
     * Delete category.
     *
     * @param id the id
     * @throws ServiceException the service exception
     */
    void deleteCategory(String  id) throws ServiceException;

}
