package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.payload.request.category.RequestUpdateCategoryDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryMapper {

    Category toCategory(ResponseCategoryDTO responseCategoryDTO);

    ResponseCategoryDTO toCategoryDto(Category category);

    void updateCategoryFromDto(RequestUpdateCategoryDTO requestUpdateCategoryDTO, @MappingTarget Category category);

}
