package com.company.storeapi.model.payload.response.pet;

import com.company.storeapi.model.entity.Breed;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.entity.Species;
import com.company.storeapi.model.enums.Origin;
import com.company.storeapi.model.enums.Sex;
import lombok.Data;

import java.util.Date;

@Data
public class ResponsePetDTO {

    private String id;
    private String name;
    private Species species;
    private Breed breed;
    private String color;
    private Sex sex;
    private Date dateBirth;
    private Integer age;
    private String particularSigns;
    private Origin origin;
    private Customer customer;
    private Date createAt;
}
