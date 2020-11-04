package com.company.storeapi.services.product.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.payload.request.product.RequestAddProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.payload.request.user.FileInfo;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.repositories.category.facade.CategoryRepositoryFacade;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.repositories.tickey.facade.TicketRepositoryFacade;
import com.company.storeapi.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepositoryFacade productRepositoryFacade;
    private final CategoryRepositoryFacade categoryRepositoryFacade;
    private final OrderRepositoryFacade orderRepositoryFacade;
    private final TicketRepositoryFacade ticketRepositoryFacade;

    private final ProductMapper productMapper;

    @Override
    public List<ResponseProductDTO> getAllProducts() {
        List<Product> products = productRepositoryFacade.getAllProduct();
        return products.stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ResponseProductDTO> getAllProductsFilters() {
        List<Product> products = productRepositoryFacade.getAllProduct();
        return products.stream().filter(product -> product.getUnit() != 0 && product.getStatus() == Status.ACTIVE).map(productMapper::toProductDto).collect(Collectors.toList());
    }

    @Override
    public ResponseProductDTO saveProduct(RequestAddProductDTO requestAddProductDTO) {
        return productMapper.toProductDto(productRepositoryFacade.saveProduct(productMapper.toProduct(requestAddProductDTO)));
    }

    @Override
    public ResponseProductDTO updateProduct(String id, RequestUpdateProductDTO requestUpdateCustomerDTO) {
        Product product = productRepositoryFacade.validateAndGetProductById(id);

        Set<ResponseCategoryDTO> listCategory =productMapper.getResponseCategoryDTOS(productMapper.toProductRequestUpdate(requestUpdateCustomerDTO));

        product.setCategory(listCategory);
        product.setUnit(requestUpdateCustomerDTO.getUnit());
        product.setUpdateAt(new Date());
        productMapper.updateProductFromDto(requestUpdateCustomerDTO, product);

        ResponseProductDTO  responseProductDTO=  productMapper.toProductDto(productRepositoryFacade.saveProduct(product));

        List<Order> orderList = orderRepositoryFacade.findOrderByProducts(responseProductDTO.getId());

        orderList.forEach(o -> {
            LinkedHashSet<ResponseOrderProductItemsDTO> listOrderProduct = new LinkedHashSet<>();

            o.getProducts().forEach(pro ->{
                Product product1 = productRepositoryFacade.validateAndGetProductById(pro.getProduct().getId());
                ResponseOrderProductItemsDTO responseOrderProductItemsDTO = new ResponseOrderProductItemsDTO();
                responseOrderProductItemsDTO.setProduct(productMapper.toProductDto(product1));
                responseOrderProductItemsDTO.setUnit(pro.getUnit());
                responseOrderProductItemsDTO.setTotal(pro.getTotal());
                listOrderProduct.add(responseOrderProductItemsDTO);
            });
            o.setProducts(listOrderProduct);
            orderRepositoryFacade.saveOrder(o);

   /*         Ticket ticket = ticketRepositoryFacade.findTicketByOrder(o.getId());
            ticket.setOrder(o);

            ticketRepositoryFacade.saveTicket(ticket);*/
        });
        return responseProductDTO;
    }


    @Override
    public ResponseProductDTO validateAndGetProductById(String id) {
        return productMapper.toProductDto(productRepositoryFacade.validateAndGetProductById(id));
    }

    @Override
    public ResponseOrderProductItemsDTO getItemsTotal(String id, int unit) {
        Product prod = productRepositoryFacade.validateAndGetProductById(id);
        ResponseOrderProductItemsDTO orderProduct = new ResponseOrderProductItemsDTO();
       if(unit<=0){
           throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"la cantidad a ingresar no puede ser 0 o menor a 0");
       }else if(unit>prod.getUnit()){
           throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"la cantidad de " + prod.getName() + " es mayor a la cantidad del presente en el inventario");
       }else{
           orderProduct.setProduct(productMapper.toProductDto(prod));
           orderProduct.setUnit(unit);
           orderProduct.setTotal(prod.getPriceSell()*unit);
       }
        return orderProduct;
    }

    @Override
    public ResponseProductDTO addUnitProduct(String id, int unit) {
        Product product = productRepositoryFacade.validateAndGetProductById(id);
        if(unit>0){
            int unitNew = product.getUnit() + unit;
            product.setStatus(Status.ACTIVE);
            product.setUnit(unitNew);
        }else{
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"la cantidad a ingresar no puede ser 0 o menor a 0");
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

    @Override
    public ResponseProductDTO updateProductWithImages(String id, RequestUpdateProductDTO requestUpdateCustomerDTO, MultipartFile file) throws IOException {
        Product product = productRepositoryFacade.validateAndGetProductById(id);
        String namePhoto = UUID.randomUUID().toString() + "-" + Objects.requireNonNull(file.getOriginalFilename())
                .replace(" ", "")
                .replace(":", "")
                .replace("\\", "");

        String fileName = StringUtils.cleanPath(namePhoto);

        FileInfo fileInfo = new FileInfo(fileName, file.getContentType(), file.getBytes());

        Set<ResponseCategoryDTO> listCategory =productMapper.getResponseCategoryDTOS(productMapper.toProductRequestUpdate(requestUpdateCustomerDTO));

        product.setCategory(listCategory);
        product.setUnit(requestUpdateCustomerDTO.getUnit());
        product.setUpdateAt(new Date());
        product.setPhoto(fileInfo);
        productMapper.updateProductFromDto(requestUpdateCustomerDTO, product);

        ResponseProductDTO  responseProductDTO=  productMapper.toProductDto(productRepositoryFacade.saveProduct(product));

        List<Order> orderList = orderRepositoryFacade.findOrderByProducts(responseProductDTO.getId());

        orderList.forEach(o -> {
            LinkedHashSet<ResponseOrderProductItemsDTO> listOrderProduct = new LinkedHashSet<>();

            o.getProducts().forEach(pro ->{
                Product product1 = productRepositoryFacade.validateAndGetProductById(pro.getProduct().getId());
                ResponseOrderProductItemsDTO responseOrderProductItemsDTO = new ResponseOrderProductItemsDTO();
                responseOrderProductItemsDTO.setProduct(productMapper.toProductDto(product1));
                responseOrderProductItemsDTO.setUnit(pro.getUnit());
                responseOrderProductItemsDTO.setTotal(pro.getTotal());
                listOrderProduct.add(responseOrderProductItemsDTO);
            });
            o.setProducts(listOrderProduct);
            orderRepositoryFacade.saveOrder(o);

            Ticket ticket = ticketRepositoryFacade.findTicketByOrder(o.getId());
            ticket.setOrder(o);

            ticketRepositoryFacade.saveTicket(ticket);
        });
        return responseProductDTO;
    }
}
