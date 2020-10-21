package com.company.storeapi.core.mapper;

import com.company.storeapi.model.payload.request.category.RequestAddCategoryDTO;
import com.company.storeapi.model.payload.request.category.RequestUpdateCategoryDTO;
import com.company.storeapi.model.payload.request.product.RequestOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryMapper {

    Category toCategory(RequestAddCategoryDTO requestAddCategoryDTO);

    Category toCategory(ResponseCategoryDTO responseCategoryDTO);

    ResponseCategoryDTO toCategoryDto(Category category);

    void updateCategoryFromDto(RequestUpdateCategoryDTO requestUpdateCategoryDTO, @MappingTarget Category category);

    public abstract  List<Category> toSetCategory(Set<ResponseCategoryDTO>  categories);

}
