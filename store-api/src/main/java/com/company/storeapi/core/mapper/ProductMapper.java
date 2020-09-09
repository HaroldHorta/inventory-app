package com.company.storeapi.core.mapper;

import com.company.storeapi.core.util.DateUtil;
import com.company.storeapi.model.dto.request.product.RequestAddProductDTO;
import com.company.storeapi.model.dto.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.dto.response.product.ResponseProductDTO;
import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.services.category.CategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class}
)
public abstract class ProductMapper {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;


    @Mapping(source = "category.id", target = "category.id")
    public abstract ResponseProductDTO toProductDto(Product product);

    public abstract void updateProductFromDto(RequestUpdateProductDTO updateOrderDto, @MappingTarget Product product);

    public abstract Product toProduct(ResponseProductDTO responseProductDTO);

    public Product toProduct(RequestAddProductDTO requestAddProductDTO){

        Product product = new Product();

        product.setName(requestAddProductDTO.getName());
        product.setDescription(requestAddProductDTO.getDescription());


        Category category =categoryMapper.toCategory(categoryService.validateAndGetCategoryById(requestAddProductDTO.getCategoryId()));
        product.setCategory(category);

        product.setStatus(Status.ACTIVE);
        product.setCreateAt(DateUtil.getDateActual());
        product.setUpdateAt(DateUtil.getDateActual());
        product.setPriceBuy(requestAddProductDTO.getPriceBuy());
        product.setPriceSell(requestAddProductDTO.getPriceSell());
        product.setUnit(requestAddProductDTO.getUnit());

        return product;

    }
}
