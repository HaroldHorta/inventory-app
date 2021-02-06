package com.company.storeapi.model.entity;

import com.company.storeapi.model.enums.Origin;
import com.company.storeapi.model.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "pet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @Id
    private String id;
    @NotNull
    private String name;
    private Species species;
    private Breed breed;
    private String color;
    private Sex sex;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateBirth;
    private Integer age;
    private String particularSigns;
    private Origin origin;
    private Customer customer;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date updateAt;

}
