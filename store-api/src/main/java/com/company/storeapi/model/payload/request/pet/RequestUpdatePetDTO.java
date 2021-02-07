package com.company.storeapi.model.payload.request.pet;

import com.company.storeapi.model.enums.Habitat;
import com.company.storeapi.model.enums.Origin;
import com.company.storeapi.model.enums.ReproductiveStatus;
import com.company.storeapi.model.enums.Sex;
import com.company.storeapi.model.payload.request.clinichistory.RequestFeeding;
import com.company.storeapi.model.payload.request.clinichistory.RequestPhysiologicalConstants;
import com.company.storeapi.model.payload.response.vaccination.ResponseVaccination;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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

    private Set<ResponseVaccination> vaccinations = new LinkedHashSet<>();
    private Set<RequestPhysiologicalConstants> physiologicalConstants = new LinkedHashSet<>();
    private Set<RequestDeworming> deworming = new LinkedHashSet<>();
    private ReproductiveStatus reproductiveStatus;
    private RequestFeeding feeding;
    @Schema(example = "Mancha cafe en el lomo")
    private String previousIllnesses;
    @Schema(example = "Mancha cafe en el lomo")
    private String surgeries;
    @Schema(example = "Mancha cafe en el lomo")
    private String familyBackground;
    private Habitat habitat;

}
