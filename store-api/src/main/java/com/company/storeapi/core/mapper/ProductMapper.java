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
public abstract class ProductMapper {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CountingGeneralService countingGeneralService;
    @Autowired
    private AssetRepositoryFacade assetRepositoryFacade;


    public abstract ResponseProductDTO toProductDto(Product product);

    public abstract RequestAddProductDTO toProductRequestUpdate(RequestUpdateProductDTO product);

    public abstract void updateProductFromDto(RequestUpdateProductDTO updateOrderDto, @MappingTarget Product product);

    public Product toProductResponse(ResponseProductDTO responseProductDTO) {
        Product product = new Product();
        product.setId(responseProductDTO.getId());
        product.setName(responseProductDTO.getName());
        product.setDescription(responseProductDTO.getDescription());

        product.setCategory(responseProductDTO.getCategory());
        product.setStatus(Status.ACTIVE);
        product.setCreateAt(new Date());
        product.setUpdateAt(new Date());
        product.setPriceBuy(responseProductDTO.getPriceBuy());
        product.setPriceSell(responseProductDTO.getPriceSell());
        product.setUnit(responseProductDTO.getUnit());

        return product;
    }

    public Product toProduct(RequestAddProductDTO requestAddProductDTO) {

        Product product = new Product();
        product.setName(requestAddProductDTO.getName());
        product.setDescription(requestAddProductDTO.getDescription());

        Set<ResponseCategoryDTO> listCategory = getResponseCategoryDTOS(requestAddProductDTO);
        product.setCategory(listCategory);
        product.setStatus(Status.ACTIVE);
        product.setCreateAt(new Date());
        product.setUpdateAt(new Date());
        product.setPriceBuy(requestAddProductDTO.getPriceBuy());
        product.setPriceSell(requestAddProductDTO.getPriceSell());
        product.setUnit(requestAddProductDTO.getUnit());
        product.setPhoto(requestAddProductDTO.getPhoto());


        product.setPhoto(requestAddProductDTO.getPhoto() == null ? ImageDefault.photo : requestAddProductDTO.getPhoto());

        List<Assets> assets = assetRepositoryFacade.getAllCustomers();
        assets.forEach(asset -> {
            double productQuantity = requestAddProductDTO.getPriceSell() * requestAddProductDTO.getUnit();
            double investment = asset.getInvestment() + productQuantity;
            asset.setEarnings(investment);
        });


        List<CountingGeneral> counting = countingGeneralService.getAllCountingGeneral();

        if ((counting.isEmpty())) {
            CountingGeneral c = new CountingGeneral();

            c.setQuantity_of_product(1);
            countingGeneralService.saveCountingGeneral(c);

        } else {
            counting.forEach(p -> {
                CountingGeneral countingGeneral = countingGeneralService.validateCountingGeneral(p.getId());

                countingGeneral.setQuantity_of_product(p.getQuantity_of_product() + 1);

                countingGeneralService.saveCountingGeneral(countingGeneral);
            });
        }
        return product;

    }

    public Set<ResponseCategoryDTO> getResponseCategoryDTOS(RequestAddProductDTO requestAddProductDTO) {
        Set<ResponseCategoryDTO> listCategory = new LinkedHashSet<>();

        requestAddProductDTO.getCategoryId().forEach(c -> {
            Category category = categoryMapper.toCategory(categoryService.validateAndGetCategoryById(c.getId()));

            ResponseCategoryDTO cat = new ResponseCategoryDTO();
            cat.setId(category.getId());
            cat.setDescription(category.getDescription());
            listCategory.add(cat);

        });
        return listCategory;
    }


}
