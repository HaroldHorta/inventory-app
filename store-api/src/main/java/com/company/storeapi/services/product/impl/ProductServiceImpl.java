package com.company.storeapi.services.product.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.CategoryMapper;
import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.core.util.ImageDefault;
import com.company.storeapi.core.util.StandNameUtil;
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
import com.company.storeapi.model.payload.response.product.ResponseListProductPaginationDto;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.repositories.finances.assets.facade.AssetRepositoryFacade;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.services.category.CategoryService;
import com.company.storeapi.services.countingGeneral.CountingGeneralService;
import com.company.storeapi.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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

    @Value("${spring.size.pagination}")
    private int size;

    @Override
    public ResponseListProductPaginationDto getAllProductInventory() {
        List<Product> products = productRepositoryFacade.getAllProduct();
        List<ResponseProductDTO> responseProductDTOList = products.stream().map(productMapper::toProductDto).collect(Collectors.toList());
        ResponseListProductPaginationDto responseListProductPaginationDto = new ResponseListProductPaginationDto();
        responseListProductPaginationDto.setProducts(responseProductDTOList);
        responseListProductPaginationDto.setLimitMax(products.size());
        return responseListProductPaginationDto;

    }

    @Override
    public ResponseListProductPaginationDto getAllProductInventory(Pageable pageable) {
        List<Product> products = productRepositoryFacade.findAllByPageable(false, pageable);
        List<ResponseProductDTO> responseProductDTOList = products.stream().map(productMapper::toProductDto).collect(Collectors.toList());
        ResponseListProductPaginationDto responseListProductPaginationDto = new ResponseListProductPaginationDto();
        responseListProductPaginationDto.setProducts(responseProductDTOList);

        int totalData = productRepositoryFacade.getAllProduct().size();

        int limitMin = getLimitInventory(pageable, 1, (pageable.getPageNumber() * size) + 1);

        int limitMax = getLimitInventory(pageable, size, (pageable.getPageNumber() + 1) * size);

        responseListProductPaginationDto.setLimitMin(limitMin);
        responseListProductPaginationDto.setLimitMax(Math.min(totalData, limitMax));
        responseListProductPaginationDto.setTotalData(totalData);
        responseListProductPaginationDto.setSize(size);
        return responseListProductPaginationDto;
    }

    private int getLimitInventory(Pageable pageable, int i, int i2) {
        return StandNameUtil.getLimitPaginator(pageable, i, i2);
    }


    @Override
    public ResponseListProductPaginationDto getAllProductsFilters() {
        List<Product> products = productRepositoryFacade.getAllProduct();
        List<ResponseProductDTO> responseProductDTOList = getResponseProduct(products);
        List<Product> productsFilter = productRepositoryFacade.getAllProduct().stream().filter(p -> p.getStatus() == Status.ACTIVO && p.getUnit() != 0).collect(Collectors.toList());
        ResponseListProductPaginationDto responseListProductPaginationDto = new ResponseListProductPaginationDto();
        responseListProductPaginationDto.setProducts(responseProductDTOList);
        responseListProductPaginationDto.setLimitMax(productsFilter.size());
        return responseListProductPaginationDto;
    }

    private List<ResponseProductDTO> getResponseProduct(List<Product> products) {
        return getResponseProductDTOS(products);
    }

    @Override
    public ResponseListProductPaginationDto getAllProductsFilters(Pageable pageable) {
        List<Product> products = productRepositoryFacade.getAllProductFilters(Status.ACTIVO, pageable);
        List<ResponseProductDTO> responseProductDTOList = getResponseProductDTOS(products);
        ResponseListProductPaginationDto responseListProductPaginationDto = new ResponseListProductPaginationDto();
        responseListProductPaginationDto.setProducts(responseProductDTOList);

        int totalData = productRepositoryFacade.countByStatus(Status.ACTIVO);

        return getResponseListProductPaginationDto(pageable, responseListProductPaginationDto, totalData);
    }

    private ResponseListProductPaginationDto getResponseListProductPaginationDto(Pageable pageable, ResponseListProductPaginationDto responseListProductPaginationDto, int totalData) {
        int limitMin = getLimitMin(pageable, 1, (pageable.getPageNumber() * size) + 1);

        int limitMax = getLimitMin(pageable, size, (pageable.getPageNumber() + 1) * size);

        responseListProductPaginationDto.setLimitMin(limitMin);
        responseListProductPaginationDto.setLimitMax(Math.min(totalData, limitMax));
        responseListProductPaginationDto.setTotalData(totalData);
        responseListProductPaginationDto.setSize(size);
        return responseListProductPaginationDto;
    }

    private int getLimitMin(Pageable pageable, int i, int i2) {
        return getLimit(pageable, i, i2);
    }

    private List<ResponseProductDTO> getResponseProductDTOS(List<Product> products) {
        return products.stream().filter(product -> product.getUnit() != 0 && product.getStatus() == Status.ACTIVO).map(productMapper::toProductDto).collect(Collectors.toList());
    }

    private int getLimit(Pageable pageable, int i, int i2) {
        return StandNameUtil.getLimitPaginator(pageable, i, i2);
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
