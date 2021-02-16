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
public class RequestAddPetDTO {

    @Schema(example = "Noa")
    private String name;
    @Schema(example = "601dfe3a718c722127fbd28f")
    private String species;
    @Schema(example = "601dca52f962052d98fe46e2")
    private String breed;
    @Schema(example = "Cafe")
    private String color;
    private Sex sex;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateBirth;
    @Schema(example = "Mancha cafe en el lomo")
    private String particularSigns;
    private Origin origin;
    @Schema(example = "601c835df4b9c71f38e3946e")
    private String customer;
    @Schema(example = "img/java.jpg")
    private String photo;

}
