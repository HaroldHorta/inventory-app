package com.company.storeapi.services.category.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.CategoryMapper;
import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.core.util.StandNameUtil;
import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.category.RequestAddCategoryDTO;
import com.company.storeapi.model.payload.request.category.RequestUpdateCategoryDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.payload.response.category.ResponseListCategoryPaginationDto;
import com.company.storeapi.repositories.category.facade.CategoryRepositoryFacade;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.services.category.CategoryService;
import com.company.storeapi.services.product.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepositoryFacade repositoryFacade;
    private final ProductRepositoryFacade productRepositoryFacade;
    private final OrderRepositoryFacade orderRepositoryFacade;

    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;


    @Override
    public List<ResponseCategoryDTO> getAllCategory() {
        List<Category> categoryList = repositoryFacade.getAllCategory();
        return categoryList.stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public ResponseCategoryDTO validateAndGetCategoryById(String id) {
        return categoryMapper.toCategoryDto(repositoryFacade.validateAndGetCategoryById(id));
    }

    @Override
    public ResponseCategoryDTO saveCategory(RequestAddCategoryDTO requestAddCategoryDTO) {
        String description = StandNameUtil.toCapitalLetters(requestAddCategoryDTO.getDescription().trim());
        boolean isDescriptionCategory = repositoryFacade.existsCategoryByDescription(description);
        if(isDescriptionCategory){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La categoría con el nombre " + description + " ya existe");
        }
        return categoryMapper.toCategoryDto(repositoryFacade.saveCategory(categoryMapper.toCategory(requestAddCategoryDTO)));
    }

    @Override
    public ResponseCategoryDTO updateCategory(RequestUpdateCategoryDTO requestUpdateCategoryDTO) {
        Category category = repositoryFacade.validateAndGetCategoryById(requestUpdateCategoryDTO.getId());
        List<Product> productList = productRepositoryFacade.findProductByCategory(requestUpdateCategoryDTO.getId());

        for (Product p : productList) {
            Product product = productRepositoryFacade.validateAndGetProductById(p.getId());
            LinkedHashSet<ResponseCategoryDTO> listCategory = new LinkedHashSet<>();
            product.getCategory().stream().map(ignored -> new ResponseCategoryDTO()).forEach(cat -> {
                cat.setId(category.getId());
                cat.setDescription(requestUpdateCategoryDTO.getDescription());
                listCategory.add(cat);
            });
            product.setCategory(listCategory);
            productRepositoryFacade.saveProduct(product);

            List<Order> orderList = orderRepositoryFacade.findOrderByProducts(product.getId());

            ProductServiceImpl.updateOrderProduct(orderList, productRepositoryFacade, productMapper, orderRepositoryFacade);


        }

        categoryMapper.updateCategoryFromDto(requestUpdateCategoryDTO, category);
        return categoryMapper.toCategoryDto(repositoryFacade.saveCategory(category));
    }

    @Override
    public void deleteById(String id) {
        List<Product> productList = productRepositoryFacade.findProductByCategory(id);
        if (productList.isEmpty()) {
            repositoryFacade.deleteCategory(id);
        } else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La categoría esta siendo usada no se puede eliminar");
        }


    }

    @Override
    public ResponseListCategoryPaginationDto getCategoryPageable() {
        List<Category> categories = repositoryFacade.getAllCategory();

        return getResponseListCategoryPaginationDto(categories);

    }

    @Override
    public ResponseListCategoryPaginationDto getCategoryPageable(Pageable pageable) {
        List<Category> categories = repositoryFacade.findAllByStatus(Status.ACTIVO, pageable);
        return getResponseListCategoryPaginationDto(categories);
    }

    public ResponseListCategoryPaginationDto getResponseListCategoryPaginationDto(List<Category> categories) {
        List<ResponseCategoryDTO> responseCustomers = categories.stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());

        ResponseListCategoryPaginationDto responseListCategoryPaginationDto = new ResponseListCategoryPaginationDto();
        responseListCategoryPaginationDto.setCategories(responseCustomers);
        responseListCategoryPaginationDto.setCount(categories.size());
        return responseListCategoryPaginationDto;
    }

}
