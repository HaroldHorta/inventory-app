package com.company.storeapi.core.mapper;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.enums.PaymentType;
import com.company.storeapi.model.enums.TicketStatus;
import com.company.storeapi.model.payload.request.ticket.RequestAddTicketDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.services.countingGeneral.CountingGeneralService;
import com.company.storeapi.services.customer.CustomerService;
import com.company.storeapi.services.order.OrderService;
import com.company.storeapi.services.product.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {OrderService.class, ProductService.class}
)
public abstract class TicketMapper {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CountingGeneralService countingGeneralService;

    @Autowired
    private ProductRepositoryFacade productRepositoryFacade;

    @Autowired
    private OrderRepositoryFacade orderRepositoryFacade;

    @Mapping(source = "customer.id",target = "customer")
    @Mapping(source = "order",target = "order")
    public abstract ResponseTicketDTO toTicketDto(Ticket ticket);

    public Ticket toTicket (RequestAddTicketDTO requestAddTicketDTO){

        Ticket ticket = new Ticket();
        Order order = orderRepositoryFacade.validateAndGetOrderById(requestAddTicketDTO.getOrder());
        ticket.setId(requestAddTicketDTO.getId());

        Customer customer = customerMapper.toCustomer(customerService.validateAndGetCustomerById(requestAddTicketDTO.getCustomerId()));
        ticket.setCustomer(customer);

       if(order.getOrderStatus() == OrderStatus.OPEN){
           order.setOrderStatus(OrderStatus.PAYED);

           orderRepositoryFacade.saveOrder(order);
           order.getProducts().forEach(p->{
                       Product product = productMapper.toProductResponse(productService.validateAndGetProductById(p.getProduct().getId()));
                       if(product.getUnit()<=0){
                           throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"Producto Agotado");
                       }else if(product.getUnit()>0){
                           int unitNew= product.getUnit()-p.getUnit();
                           if(unitNew<=0){
                               unitNew=0;
                               product.setStatus(Status.INACTIVE);
                           }
                           product.setUnit(unitNew);

                           getUpdateCategory(product);
                       }
                   }
           );
           ticket.setOrder(order);
       }else{
           throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "La orden ya esta pagada, no se puede generar ticket");
       }

        ticket.setCreateAt(new Date());
        ticket.setPaymentType(requestAddTicketDTO.getPaymentType());

        if(requestAddTicketDTO.getPaymentType() == PaymentType.CASH || requestAddTicketDTO.getPaymentType() == PaymentType.TRANSACTION){
            ticket.setTicketStatus(TicketStatus.PAYED);
        }else {
            ticket.setTicketStatus(TicketStatus.CREDIT);
        }

        countingGeneralService.counting(requestAddTicketDTO.getOrder(), order.getOrderStatus());
        return ticket;
    }

    public void getUpdateCategory(Product product) {
        Set<ResponseCategoryDTO> listCategory = new LinkedHashSet<>();
        product.getCategory().forEach(c ->{
            ResponseCategoryDTO cat = new ResponseCategoryDTO();
            cat.setId(c.getId());
            cat.setDescription(c.getDescription());
            listCategory.add(cat);
        });
        product.setCategory(listCategory);
        productMapper.toProductDto(productRepositoryFacade.saveProduct(product));
    }
}
