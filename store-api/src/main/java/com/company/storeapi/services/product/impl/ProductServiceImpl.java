package com.company.storeapi.services.product.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.CategoryMapper;
import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.core.util.ImageDefault;
import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.entity.CountingGeneral;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.entity.finance.Assets;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.product.RequestAddProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateUnitDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.repositories.finances.assets.facade.AssetRepositoryFacade;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.services.category.CategoryService;
import com.company.storeapi.services.countingGeneral.CountingGeneralService;
import com.company.storeapi.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CountingGeneralService countingGeneralService;
    private final AssetRepositoryFacade assetRepositoryFacade;

    private final ProductRepositoryFacade productRepositoryFacade;
    private final OrderRepositoryFacade orderRepositoryFacade;

    private final ProductMapper productMapper;

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Override
    public List<ResponseProductDTO> getAllProductInventory() {
        List<Product> products = productRepositoryFacade.getAllProduct();
        return products.stream().map(productMapper::toProductDto).collect(Collectors.toList());

    }

    @Override
    public List<ResponseProductDTO> getAllProducts() {
        List<Product> products = productRepositoryFacade.getAllProduct();
        return products.stream().filter(product -> product.getStatus()==Status.ACTIVO).sorted(Comparator.comparing(Product::getName)).map(productMapper::toProductDto).collect(Collectors.toList());
    }

    @Override
    public ResponseProductDTO saveProduct(RequestAddProductDTO requestAddProductDTO) {

        Product product = new Product();
        product.setName(requestAddProductDTO.getName());
        product.setDescription(requestAddProductDTO.getDescription());

        Set<ResponseCategoryDTO> listCategory = getResponseCategoryDTOS(requestAddProductDTO);
        product.setCategory(listCategory);
        product.setStatus(Status.ACTIVO);
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
        countingGeneralProducts();
        return productMapper.toProductDto(productRepositoryFacade.saveProduct(product));
    }

    private void countingGeneralProducts() {
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
    }

    @Override
    public ResponseProductDTO updateProduct(String id, RequestUpdateProductDTO requestUpdateCustomerDTO) {
        Product product = productRepositoryFacade.validateAndGetProductById(id);

        Set<ResponseCategoryDTO> listCategory = getResponseCategoryDTOS(productMapper.toProductRequestUpdate(requestUpdateCustomerDTO));

        product.setCategory(listCategory);
        product.setUpdateAt(new Date());
        product.setUnit(product.getUnit());
        productMapper.updateProductFromDto(requestUpdateCustomerDTO, product);

        ResponseProductDTO responseProductDTO = productMapper.toProductDto(productRepositoryFacade.saveProduct(product));

        List<Order> orderList = orderRepositoryFacade.findOrderByProducts(responseProductDTO.getId());

        updateOrderProduct(orderList, productRepositoryFacade, productMapper, orderRepositoryFacade);
        return responseProductDTO;
    }

    public static void updateOrderProduct(List<Order> orderList, ProductRepositoryFacade productRepositoryFacade, ProductMapper productMapper, OrderRepositoryFacade orderRepositoryFacade) {
        orderList.forEach(order -> getListOrderProduct(productRepositoryFacade, productMapper, orderRepositoryFacade, order));
    }

    @Override
    public ResponseProductDTO validateAndGetProductById(String id) {
        return productMapper.toProductDto(productRepositoryFacade.validateAndGetProductById(id));
    }

    @Override
    public ResponseOrderProductItemsDTO getItemsTotal(String id, int unit) {
        Product prod = productRepositoryFacade.validateAndGetProductById(id);
        ResponseOrderProductItemsDTO orderProduct = new ResponseOrderProductItemsDTO();
        if (unit <= 0) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "la cantidad a ingresar no puede ser 0 o menor a 0");
        } else if (unit > prod.getUnit()) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "la cantidad de " + prod.getName() + " es mayor a la cantidad del presente en el inventario");
        } else {
            orderProduct.setProduct(productMapper.toProductDto(prod));
            orderProduct.setUnit(unit);
            orderProduct.setTotal(prod.getPriceSell() * unit);
        }
        return orderProduct;
    }

    @Override
    public ResponseProductDTO addUnitProduct(RequestUpdateUnitDTO requestUpdateUnitDTO) {
        Product product = productRepositoryFacade.validateAndGetProductById(requestUpdateUnitDTO.getId());
        if (requestUpdateUnitDTO.getUnit() > 0) {
            int unitNew = product.getUnit() + requestUpdateUnitDTO.getUnit();
            product.setStatus(Status.ACTIVO);
            product.setUnit(unitNew);
            product.setPriceBuy(requestUpdateUnitDTO.getPriceBuy());
            product.setPriceSell(requestUpdateUnitDTO.getPriceSell());
        } else {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "la cantidad a ingresar no puede ser 0 o menor a 0");
        }
        return productMapper.toProductDto(productRepositoryFacade.saveProduct(product));
    }

    @Override
    public ResponseProductDTO updateStatus(String id, Status status) {
        Product product = productRepositoryFacade.validateAndGetProductById(id);
        product.setStatus(status);
        return productMapper.toProductDto(productRepositoryFacade.saveProduct(product));

    }

    @Override
    public List<ResponseProductDTO> findProductByCategory(String id) {
        List<Product> products = productRepositoryFacade.findProductByCategory(id);
        return products.stream().map(productMapper::toProductDto).collect(Collectors.toList());
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

    private static void getListOrderProduct(ProductRepositoryFacade productRepositoryFacade, ProductMapper productMapper, OrderRepositoryFacade orderRepositoryFacade, Order order) {
        LinkedHashSet<ResponseOrderProductItemsDTO> listOrderProduct = new LinkedHashSet<>();
        if (order.getOrderStatus() == OrderStatus.ABIERTA) {
            order.getProducts().forEach(pro -> {
                Product productNew = productRepositoryFacade.validateAndGetProductById(pro.getProduct().getId());
                ResponseOrderProductItemsDTO responseOrderProductItemsDTO = new ResponseOrderProductItemsDTO();
                responseOrderProductItemsDTO.setProduct(productMapper.toProductDto(productNew));
                responseOrderProductItemsDTO.setUnit(pro.getUnit());
                responseOrderProductItemsDTO.setTotal(pro.getTotal());
                listOrderProduct.add(responseOrderProductItemsDTO);
            });
            order.setProducts(listOrderProduct);
            orderRepositoryFacade.saveOrder(order);
        }
    }



}
