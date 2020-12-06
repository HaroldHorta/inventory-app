package com.company.storeapi.services.product.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.product.RequestAddProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateUnitDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.payload.response.product.ResponseListProductPaginationDto;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.services.product.ProductService;
import lombok.RequiredArgsConstructor;
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

    private final ProductRepositoryFacade productRepositoryFacade;
    private final OrderRepositoryFacade orderRepositoryFacade;

    private final ProductMapper productMapper;


    @Override
    public List<ResponseProductDTO> getAllProduct() {
        List<Product> products = productRepositoryFacade.getAllProduct();
        return products.stream().map(productMapper::toProductDto).collect(Collectors.toList());

    }

    @Override
    public ResponseListProductPaginationDto getAllProductsFilters() {
        List<Product> products = productRepositoryFacade.getAllProduct();
        return getResponseListProductPaginationDto(products);
    }

    public ResponseListProductPaginationDto getResponseListProductPaginationDto(List<Product> products) {
        List<ResponseProductDTO> responseProductDTOList = products.stream().filter(product -> product.getUnit() != 0 && product.getStatus() == Status.ACTIVE).map(productMapper::toProductDto).collect(Collectors.toList());
        List<Product> productsFilter = productRepositoryFacade.getAllProduct().stream().filter(p -> p.getStatus() == Status.ACTIVE && p.getUnit() != 0).collect(Collectors.toList());
        ResponseListProductPaginationDto responseListProductPaginationDto = new ResponseListProductPaginationDto();
        responseListProductPaginationDto.setProducts(responseProductDTOList);
        responseListProductPaginationDto.setCount(productsFilter.size());
        return responseListProductPaginationDto;
    }

    @Override
    public ResponseListProductPaginationDto getAllProductsFilters(Pageable pageable) {
        List<Product> products = productRepositoryFacade.getAllProductFilters(Status.ACTIVE, pageable);
        return getResponseListProductPaginationDto(products);
    }


    @Override
    public ResponseProductDTO saveProduct(RequestAddProductDTO requestAddProductDTO) {
        return productMapper.toProductDto(productRepositoryFacade.saveProduct(productMapper.toProduct(requestAddProductDTO)));
    }

    @Override
    public ResponseProductDTO updateProduct(String id, RequestUpdateProductDTO requestUpdateCustomerDTO) {
        Product product = productRepositoryFacade.validateAndGetProductById(id);

        Set<ResponseCategoryDTO> listCategory = productMapper.getResponseCategoryDTOS(productMapper.toProductRequestUpdate(requestUpdateCustomerDTO));

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

    private static void getListOrderProduct(ProductRepositoryFacade productRepositoryFacade, ProductMapper productMapper, OrderRepositoryFacade orderRepositoryFacade, Order order) {
        LinkedHashSet<ResponseOrderProductItemsDTO> listOrderProduct = new LinkedHashSet<>();
        if (order.getOrderStatus() == OrderStatus.OPEN) {
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
            product.setStatus(Status.ACTIVE);
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


}
