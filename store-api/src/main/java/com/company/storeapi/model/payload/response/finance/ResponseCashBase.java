package com.company.storeapi.model.payload.response.finance;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ResponseCashBase {

    private double dailyCashBase;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;

}
