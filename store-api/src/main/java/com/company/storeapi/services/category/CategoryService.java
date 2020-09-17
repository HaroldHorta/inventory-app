package com.company.storeapi.services.category;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.payload.request.category.RequestAddCategoryDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;

import java.util.List;

public interface CategoryService {

    List<ResponseCategoryDTO> getAllCategory() throws ServiceException;

    ResponseCategoryDTO validateAndGetCategoryById(String id);

    ResponseCategoryDTO saveCategory(RequestAddCategoryDTO requestAddCategoryDTO) throws ServiceException;

    ResponseCategoryDTO updateCategory( String id, RequestAddCategoryDTO requestAddCategoryDTO) throws ServiceException;

    void deleteById(String id) throws ServiceException;

}
