package com.company.storeapi.model.payload.request.pet;

import com.company.storeapi.model.enums.Option;
import lombok.Data;

import java.util.Date;

@Data
public class RequestDeworming {

    private Option option;
    private String description;
    private Date dewormingDate;
    private String product;

}
