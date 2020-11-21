package com.company.storeapi.web.advisers;

import com.company.storeapi.web.advisers.base.BaseRestAdviser;
import com.company.storeapi.web.api.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * The type Person rest advicer.
 */
@Slf4j
@ControllerAdvice(assignableTypes = {

        CategoryRestApi.class,
        CountingGeneralRestApi.class,
        CustomerRestApi.class,
        FileUploadRestApi.class,
        InventoryRestApi.class,
        OrderRestApi.class,
        ProductRestApi.class,
        TicketRestApi.class,
        UserRestApi.class,


})
public class GeneralRestAdvice extends BaseRestAdviser {


}
