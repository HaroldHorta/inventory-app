package com.company.storeapi.model.entity;

import com.company.storeapi.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "specie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Species {

    @Id
    private String id;
    @NotNull
    private String description;
    private Status status;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
}
