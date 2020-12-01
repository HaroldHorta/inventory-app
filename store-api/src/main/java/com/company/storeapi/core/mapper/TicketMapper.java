package com.company.storeapi.core.mapper;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.enums.*;
import com.company.storeapi.model.payload.request.ticket.RequestAddTicketDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.payload.response.finance.CreditCapital;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;
import com.company.storeapi.repositories.finances.cashRegisterDaily.facade.CashRegisterDailyRepositoryFacade;
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

import java.util.*;

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
    private CountingGeneralService countingGeneralService;

    @Autowired
    private ProductRepositoryFacade productRepositoryFacade;

    @Autowired
    private OrderRepositoryFacade orderRepositoryFacade;

    @Autowired
    private CashRegisterDailyRepositoryFacade cashRegisterDailyRepositoryFacade;

    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "order", target = "order")
    @Mapping(source = "creditCapitals", target = "creditCapitals")
    public abstract ResponseTicketDTO toTicketDto(Ticket ticket);

    public Ticket toTicket(RequestAddTicketDTO requestAddTicketDTO) {

        Order order = orderRepositoryFacade.validateAndGetOrderById(requestAddTicketDTO.getOrder());

        if (!(order.getOrderStatus() == OrderStatus.OPEN)) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "La orden ya esta pagada, no se puede generar ticket");
        }

        double dailyCashSales;
        double dailyTransactionsSales = 0;
        double dailyCreditSales = 0;
        double cashCreditCapital = 0;
        double transactionCreditCapital = 0;

        Ticket ticket = new Ticket();
        ticket.setId(requestAddTicketDTO.getId());
        Customer customer = customerMapper.toCustomer(customerService.validateAndGetCustomerById(requestAddTicketDTO.getCustomerId()));
        ticket.setCustomer(customer);
        order.setOrderStatus(OrderStatus.PAYED);

        orderRepositoryFacade.saveOrder(order);

        changeStatusProductByUnit(ticket, order);
        ticket.setCreateAt(new Date());
        ticket.setPaymentType(requestAddTicketDTO.getPaymentType());
        ticket.setTicketStatus(TicketStatus.PAYED);
        ticket.setOutstandingBalance(0);

        double getTicketCostWithoutIVA = (IVA.IVA19 * order.getTotalOrder()) / IVA.PORCENTAJE;

        ticket.setTicketCost(order.getTotalOrder());
        ticket.setTicketCostWithoutIVA(getTicketCostWithoutIVA);
        ticket.setCashPayment(order.getTotalOrder());
        ticket.setTransactionPayment(0);
        ticket.setCreditPayment(0);

        dailyCashSales = order.getTotalOrder();

        dailyTransactionsSales = validateDailyTransactionsSales(order, dailyTransactionsSales, ticket);

        if (ticket.getPaymentType() == PaymentType.CREDIT) {
            ticket.setTransactionPayment(0);
            ticket.setCashPayment(0);
            ticket.setCreditPayment(order.getTotalOrder());

            dailyCreditSales = order.getTotalOrder();

            if (requestAddTicketDTO.getCreditCapital() != 0) {

                Set<CreditCapital> creditCapitals = new LinkedHashSet<>();

                CreditCapital creditCapital = new CreditCapital();
                creditCapital.setCashCreditCapital(requestAddTicketDTO.getCreditCapital());
                creditCapital.setTransactionCreditCapital(0);
                creditCapital.setPaymentType(requestAddTicketDTO.getCreditPaymentType());

                cashCreditCapital = requestAddTicketDTO.getCreditCapital();

                transactionCreditCapital = validateTransactionCreditCapital(requestAddTicketDTO, transactionCreditCapital, creditCapital);

                creditCapital.setCreatAt(new Date());

                creditCapitals.add(creditCapital);
                ticket.setCreditCapitals(creditCapitals);

            }

            ticket.setTicketStatus(TicketStatus.CREDIT);

            double balance = order.getTotalOrder() - requestAddTicketDTO.getCreditCapital();

            ticket.setOutstandingBalance(requestAddTicketDTO.getCreditCapital() == 0 ? order.getTotalOrder() : balance);

        }

        getCashRegisterDaily(dailyCashSales, dailyTransactionsSales, dailyCreditSales, cashCreditCapital, transactionCreditCapital);

        countingGeneralService.counting(requestAddTicketDTO.getOrder(), order.getOrderStatus());
        return ticket;
    }

    public void getCashRegisterDaily(double dailyCashSales, double dailyTransactionsSales, double dailyCreditSales, double cashCreditCapital, double transactionCreditCapital) {
        if (cashRegisterDailyRepositoryFacade.existsCashRegisterDailiesByCashRegister(false)) {
            CashRegisterDaily cashRegisterDaily = cashRegisterDailyRepositoryFacade.findCashRegisterDailyByUltimate();
            cashRegisterDaily.setDailyCashSales(cashRegisterDaily.getDailyCashSales() + dailyCashSales);
            cashRegisterDaily.setDailyTransactionsSales(cashRegisterDaily.getDailyTransactionsSales() + dailyTransactionsSales);
            cashRegisterDaily.setDailyCreditSales(cashRegisterDaily.getDailyCreditSales() + dailyCreditSales);
            cashRegisterDaily.setCashCreditCapital(cashRegisterDaily.getCashCreditCapital() + cashCreditCapital);
            cashRegisterDaily.setTransactionCreditCapital(cashRegisterDaily.getTransactionCreditCapital() + transactionCreditCapital);
            cashRegisterDaily.setCashRegister(false);

            cashRegisterDailyRepositoryFacade.save(cashRegisterDaily);
        } else {
            CashRegisterDaily cashRegisterDaily = new CashRegisterDaily();
            cashRegisterDaily.setCreateAt(new Date());

            cashRegisterDailyRepositoryFacade.save(cashRegisterDaily);
        }
    }

    public double validateTransactionCreditCapital(RequestAddTicketDTO requestAddTicketDTO, double transactionCreditCapital, CreditCapital creditCapital) {
        if (requestAddTicketDTO.getCreditPaymentType() == PaymentType.TRANSACTION) {
            creditCapital.setCashCreditCapital(0);
            creditCapital.setTransactionCreditCapital(requestAddTicketDTO.getCreditCapital());
            transactionCreditCapital = requestAddTicketDTO.getCreditCapital();
        }
        return transactionCreditCapital;
    }

    public double validateDailyTransactionsSales(Order order, double dailyTransactionsSales, Ticket ticket) {
        if (ticket.getPaymentType() == PaymentType.TRANSACTION) {
            ticket.setTransactionPayment(order.getTotalOrder());
            ticket.setCashPayment(0);
            ticket.setCreditPayment(0);

            dailyTransactionsSales = order.getTotalOrder();
        }
        return dailyTransactionsSales;
    }

    private void changeStatusProductByUnit(Ticket ticket, Order order) {
        order.getProducts().forEach(p -> {
                    Product product = productRepositoryFacade.validateAndGetProductById(p.getProduct().getId());
                    if (product.getUnit() <= 0) {
                        throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "Producto Agotado");
                    }
                    int unitNew = product.getUnit() - p.getUnit();
                    if (unitNew <= 0) {
                        unitNew = 0;
                        product.setStatus(Status.INACTIVE);
                    }
                    product.setUnit(unitNew);

                    getUpdateCategory(product);

                    productMapper.toProductDto(productRepositoryFacade.saveProduct(product));
                }
        );
        ticket.setOrder(order);
    }

    public void getUpdateCategory(Product product) {
        Set<ResponseCategoryDTO> listCategory = new LinkedHashSet<>();
        product.getCategory().forEach(c -> {
            ResponseCategoryDTO cat = new ResponseCategoryDTO();
            cat.setId(c.getId());
            cat.setDescription(c.getDescription());
            listCategory.add(cat);
        });
        product.setCategory(listCategory);

    }

}
