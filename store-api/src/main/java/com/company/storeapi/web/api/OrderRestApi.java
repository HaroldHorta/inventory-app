package com.company.storeapi.web.api;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.dto.request.order.RequestAddOrderDTO;
import com.company.storeapi.model.dto.request.order.RequestUpdateOrderDTO;
import com.company.storeapi.model.dto.response.order.ResponseOrderDTO;
import com.company.storeapi.services.order.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/order")
@CrossOrigin({"*"})
public class OrderRestApi {

    private final OrderService orderService;

    public OrderRestApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseOrderDTO> getAllOrders() throws ServiceException {
        return orderService.getAllOrders();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseOrderDTO> save(@RequestBody RequestAddOrderDTO order) throws ServiceException{
        ResponseOrderDTO newOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(newOrder, new HttpHeaders(), HttpStatus.OK);
    }

   @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseOrderDTO> readById(@PathVariable("id") String id)
            throws ServiceException {
        ResponseOrderDTO entity = orderService.validateAndGetOrderById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "{id}", produces =  MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseOrderDTO> update (@PathVariable String id, @Valid @RequestBody RequestUpdateOrderDTO requestUpdateOrderDTO)
            throws ServiceException{
        ResponseOrderDTO orderDTO = orderService.updateOrder(id,requestUpdateOrderDTO);
        return new ResponseEntity<>(orderDTO, new HttpHeaders(), HttpStatus.OK);

    }

}
