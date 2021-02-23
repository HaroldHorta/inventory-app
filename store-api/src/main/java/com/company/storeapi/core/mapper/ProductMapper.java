package com.company.storeapi.core.mapper;

import com.company.storeapi.core.util.ImageDefault;
import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.entity.CountingGeneral;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.entity.finance.Assets;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.product.RequestAddProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.repositories.finances.assets.facade.AssetRepositoryFacade;
import com.company.storeapi.services.category.CategoryService;
import com.company.storeapi.services.countingGeneral.CountingGeneralService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class}
)
public interface ProductMapper {

    ResponseProductDTO toProductDto(Product product);

    RequestAddProductDTO toProductRequestUpdate(RequestUpdateProductDTO product);

    void updateProductFromDto(RequestUpdateProductDTO updateOrderDto, @MappingTarget Product product);


}
