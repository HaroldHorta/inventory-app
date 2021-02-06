package com.company.storeapi.model.payload.request.pet;

import com.company.storeapi.model.enums.Origin;
import com.company.storeapi.model.enums.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdatePetDTO {

    private String id;
    @Schema(example = "Sam")
    private String name;
    private String species;
    private String breed;
    @Schema(example = "Cafe")
    private String color;
    private Sex sex;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateBirth;
    @Schema(example = "2")
    private Integer age;
    @Schema(example = "Mancha cafe en el lomo")
    private String particularSigns;
    private Origin origin;
    private String customer;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
}
