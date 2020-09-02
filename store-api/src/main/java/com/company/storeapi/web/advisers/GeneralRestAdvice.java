package com.company.storeapi.web.advisers;

import com.company.storeapi.web.advisers.base.BaseRestAdviser;
import com.company.storeapi.web.api.CategoryRestApi;
import com.company.storeapi.web.api.CustomerRestApi;
import com.company.storeapi.web.api.OrderRestApi;
import com.company.storeapi.web.api.ProductRestApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * The type Person rest advicer.
 */
@Slf4j
@ControllerAdvice(assignableTypes = {

        CategoryRestApi.class,
        ProductRestApi.class,
        OrderRestApi.class,
        CustomerRestApi.class

})
public class GeneralRestAdvice extends BaseRestAdviser {


}
