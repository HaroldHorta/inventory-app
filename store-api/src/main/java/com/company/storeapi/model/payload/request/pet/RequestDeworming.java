package com.company.storeapi.model.payload.request.pet;

import com.company.storeapi.model.enums.Option;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RequestDeworming {

    private Option option;
    private String description;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dewormingDate;
    private String product;

}
