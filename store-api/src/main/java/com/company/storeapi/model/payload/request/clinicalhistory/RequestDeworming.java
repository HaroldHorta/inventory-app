package com.company.storeapi.model.payload.request.clinicalhistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeworming {

    @Id
    private String id;
    @NotNull
    private String description;
    private Date dewormingDate;
    private String product;

}
