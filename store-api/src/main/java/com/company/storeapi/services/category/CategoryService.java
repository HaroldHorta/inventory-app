package com.company.storeapi.services.category;

import com.company.storeapi.model.payload.request.category.RequestAddCategoryDTO;
import com.company.storeapi.model.payload.request.category.RequestUpdateCategoryDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.payload.response.category.ResponseListCategoryPaginationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    List<ResponseCategoryDTO> getAllCategory() ;

    ResponseCategoryDTO validateAndGetCategoryById(String id);

    ResponseCategoryDTO saveCategory(RequestAddCategoryDTO requestAddCategoryDTO) ;

    ResponseCategoryDTO updateCategory( RequestUpdateCategoryDTO requestUpdateCategoryDTO) ;

    void deleteById(String id) ;

    ResponseListCategoryPaginationDto getCategoryPageable();

    ResponseListCategoryPaginationDto getCategoryPageable(Pageable pageable);


}
