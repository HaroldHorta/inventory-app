package com.company.storeapi.model.entity;

import com.company.storeapi.model.enums.Origin;
import com.company.storeapi.model.enums.ReproductiveStatus;
import com.company.storeapi.model.enums.Sex;
import com.company.storeapi.model.payload.request.clinichistory.RequestFeeding;
import com.company.storeapi.model.payload.request.clinichistory.RequestHabitat;
import com.company.storeapi.model.payload.request.pet.RequestPatientHistoryDeworming;
import com.company.storeapi.model.payload.request.pet.RequestPatientHistoryVaccinations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private String particularSigns;
    private Origin origin;
    private Customer customer;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date updateAt;
    private String photo;
    private Set<RequestPatientHistoryVaccinations> vaccinations = new LinkedHashSet<>();
    private Set<RequestPatientHistoryDeworming> dewormingInternal = new LinkedHashSet<>();
    private Set<RequestPatientHistoryDeworming> dewormingExternal = new LinkedHashSet<>();
    private ReproductiveStatus reproductiveStatus;
    private RequestFeeding feeding;
    private String previousIllnesses;
    private String surgeries;
    private String familyBackground;
    private RequestHabitat habitat;
    private String allergy;


}
