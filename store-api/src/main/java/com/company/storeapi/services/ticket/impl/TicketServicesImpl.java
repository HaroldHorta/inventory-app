package com.company.storeapi.services.ticket.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.CustomerMapper;
import com.company.storeapi.core.mapper.ProductMapper;
import com.company.storeapi.core.mapper.TicketMapper;
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
import com.company.storeapi.repositories.finances.cashregisterdaily.facade.CashRegisterDailyRepositoryFacade;
import com.company.storeapi.repositories.order.facade.OrderRepositoryFacade;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
import com.company.storeapi.repositories.tickey.facade.TicketRepositoryFacade;
import com.company.storeapi.services.countingGeneral.CountingGeneralService;
import com.company.storeapi.services.customer.CustomerService;
import com.company.storeapi.services.ticket.TicketServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServicesImpl implements TicketServices {

    private final ProductMapper productMapper;

    private final CustomerMapper customerMapper;

    private final CustomerService customerService;

    private final CountingGeneralService countingGeneralService;

    private final ProductRepositoryFacade productRepositoryFacade;

    private final OrderRepositoryFacade orderRepositoryFacade;

    private final TicketRepositoryFacade ticketRepositoryFacade;
    private final TicketMapper ticketMapper;
    private final CashRegisterDailyRepositoryFacade cashRegisterDailyRepositoryFacade;

    @Override
    public List<ResponseTicketDTO> getAllTicket() {
        return ticketRepositoryFacade.getAllTicket().stream().map(ticketMapper::toTicketDto).collect(Collectors.toList());
    }

    @Override
    public ResponseTicketDTO validateAndGetTicketById(String id) {
        return ticketMapper.toTicketDto(ticketRepositoryFacade.validateAndGetTicketById(id));
    }

    @Override
    public ResponseTicketDTO saveTicket(RequestAddTicketDTO requestAddTicketDTO) {

        Order order = orderRepositoryFacade.validateAndGetOrderById(requestAddTicketDTO.getOrder());

        if ((order.getOrderStatus() != OrderStatus.ABIERTA)) {
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
        order.setOrderStatus(OrderStatus.PAGADA);

        orderRepositoryFacade.saveOrder(order);

        changeStatusProductByUnit(ticket, order);
        ticket.setCreateAt(new Date());
        ticket.setPaymentType(requestAddTicketDTO.getPaymentType());
        ticket.setTicketStatus(TicketStatus.PAGADA);
        ticket.setOutstandingBalance(0);

        double getTicketCostWithoutIVA = (IVA.IVA19 * order.getTotalOrder()) / IVA.PORCENTAJE;

        ticket.setTicketCost(order.getTotalOrder());
        ticket.setTicketCostWithoutIVA(getTicketCostWithoutIVA);
        ticket.setCashPayment(order.getTotalOrder());
        ticket.setTransactionPayment(0);
        ticket.setCreditPayment(0);

        dailyCashSales = order.getTotalOrder();

        dailyTransactionsSales = validateDailyTransactionsSales(order, dailyTransactionsSales, ticket);

        if (ticket.getPaymentType() == PaymentType.CREDITO) {
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

            ticket.setTicketStatus(TicketStatus.CREDITO);

            double balance = order.getTotalOrder() - requestAddTicketDTO.getCreditCapital();

            ticket.setOutstandingBalance(requestAddTicketDTO.getCreditCapital() == 0 ? order.getTotalOrder() : balance);

        }

        getCashRegisterDaily(dailyCashSales, dailyTransactionsSales, dailyCreditSales, cashCreditCapital, transactionCreditCapital);

        countingGeneralService.counting(requestAddTicketDTO.getOrder(), order.getOrderStatus());

        return ticketMapper.toTicketDto(ticketRepositoryFacade.saveTicket(ticket));
    }

    @Override
    public List<ResponseTicketDTO> findTicketByCustomer_NroDocument(String nroDocument) {
        List<Ticket> tickets = ticketRepositoryFacade.findTicketByCustomer_NroDocument(nroDocument);
        if (tickets.isEmpty()) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "El cliente no tiene ticket asosiados");
        }
        return tickets.stream().map(ticketMapper::toTicketDto).collect(Collectors.toList());
    }

    @Override
    public ResponseTicketDTO updateCreditCapital(String idTicket, double creditCapital, PaymentType creditPayment) {

        if (creditCapital <= 0) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "El abono debe ser superior a 0");

        }

        Ticket ticket = ticketRepositoryFacade.validateAndGetTicketById(idTicket);

        if (!(cashRegisterDailyRepositoryFacade.existsCashRegisterDailiesByCashRegister(false))) {

            CashRegisterDaily cashRegisterDaily = new CashRegisterDaily();
            cashRegisterDaily.setCreateAt(new Date());
            cashRegisterDailyRepositoryFacade.save(cashRegisterDaily);

        }

        CashRegisterDaily cashRegisterDaily = cashRegisterDailyRepositoryFacade.findCashRegisterDailyByUltimate();

        if (ticket.getTicketStatus() == TicketStatus.PAGADA) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La orden ha sido cancelada en su totalidad");
        }

        double credit = ticket.getOutstandingBalance() - creditCapital;

        Set<CreditCapital> creditCapitals = ticket.getCreditCapitals();

        CreditCapital creditCap = new CreditCapital();
        creditCap.setCashCreditCapital(creditCapital);
        creditCap.setTransactionCreditCapital(0);

        cashRegisterDaily.setCashCreditCapital(cashRegisterDaily.getCashCreditCapital() + creditCapital);
        if (creditPayment == PaymentType.TRANSACCION) {
            creditCap.setCashCreditCapital(0);
            creditCap.setTransactionCreditCapital(creditCapital);

            cashRegisterDaily.setTransactionCreditCapital(cashRegisterDaily.getTransactionCreditCapital() + creditCapital);

        }
        creditCap.setCreatAt(new Date());
        creditCap.setPaymentType(creditPayment);
        creditCapitals.add(creditCap);
        ticket.setCreditCapitals(creditCapitals);
        ticket.setOutstandingBalance(credit);

        if (credit <= 0) {
            ticket.setOutstandingBalance(0);
            ticket.setTicketStatus(TicketStatus.PAGADA);
            ticket.setCashRegister(false);
        }
        cashRegisterDailyRepositoryFacade.save(cashRegisterDaily);
        return ticketMapper.toTicketDto(ticketRepositoryFacade.saveTicket(ticket));

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
            cashRegisterDaily.setDailyCashSales(dailyCashSales);
            cashRegisterDaily.setDailyTransactionsSales(dailyTransactionsSales);
            cashRegisterDaily.setDailyCreditSales(dailyCreditSales);
            cashRegisterDaily.setCashCreditCapital(cashCreditCapital);
            cashRegisterDaily.setTransactionCreditCapital(transactionCreditCapital);
            cashRegisterDaily.setCashRegister(false);

            cashRegisterDailyRepositoryFacade.save(cashRegisterDaily);
        }
    }


    public double validateTransactionCreditCapital(RequestAddTicketDTO requestAddTicketDTO, double transactionCreditCapital, CreditCapital creditCapital) {
        if (requestAddTicketDTO.getCreditPaymentType() == PaymentType.TRANSACCION) {
            creditCapital.setCashCreditCapital(0);
            creditCapital.setTransactionCreditCapital(requestAddTicketDTO.getCreditCapital());
            transactionCreditCapital = requestAddTicketDTO.getCreditCapital();
        }
        return transactionCreditCapital;
    }

    public double validateDailyTransactionsSales(Order order, double dailyTransactionsSales, Ticket ticket) {
        if (ticket.getPaymentType() == PaymentType.TRANSACCION) {
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
                        product.setStatus(Status.INACTIVO);
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
