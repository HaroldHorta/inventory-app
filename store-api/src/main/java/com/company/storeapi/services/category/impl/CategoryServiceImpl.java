package com.company.storeapi.services.category.impl;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.core.mapper.CategoryMapper;
import com.company.storeapi.model.dto.request.category.RequestAddCategoryDTO;
import com.company.storeapi.model.dto.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.entity.Category;
import com.company.storeapi.repositories.category.facade.CategoryRepositoryFacade;
import com.company.storeapi.services.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepositoryFacade repositoryFacade;

    private final CategoryMapper categoryMapper;


    @Override
    public List<ResponseCategoryDTO> getAllCategory() throws ServiceException {
        List<Category> categoryList = repositoryFacade.getAllCategory();
        return categoryList.stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public ResponseCategoryDTO validateAndGetCategoryById(String id){
        return categoryMapper.toCategoryDto(repositoryFacade.validateAndGetCategoryById(id));
    }

    @Override
    public ResponseCategoryDTO saveCategory(RequestAddCategoryDTO requestAddCategoryDTO) throws ServiceException {
        return categoryMapper.toCategoryDto(repositoryFacade.saveCategory(categoryMapper.toCategory(requestAddCategoryDTO)));
    }

    @Override
    public ResponseCategoryDTO updateCategory(String id, RequestAddCategoryDTO requestAddCategoryDTO) throws ServiceException {
        Category category = repositoryFacade.validateAndGetCategoryById(id);
        categoryMapper.updateCategoryFromDto(requestAddCategoryDTO, category);
        return categoryMapper.toCategoryDto(repositoryFacade.saveCategory(category));
    }

    @Override
    public void deleteById(String id) throws ServiceException {
        repositoryFacade.deleteCategory(id);
    }

}
