package com.company.storeapi.model.payload.response.pet;

import com.company.storeapi.model.entity.Breed;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.entity.Species;
import com.company.storeapi.model.enums.Habitat;
import com.company.storeapi.model.enums.Origin;
import com.company.storeapi.model.enums.ReproductiveStatus;
import com.company.storeapi.model.enums.Sex;
import com.company.storeapi.model.payload.request.clinichistory.RequestFeeding;
import com.company.storeapi.model.payload.request.clinichistory.RequestPhysiologicalConstants;
import com.company.storeapi.model.payload.request.pet.RequestDeworming;
import com.company.storeapi.model.payload.request.pet.RequestPatientHistoryVaccinations;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class ResponsePetDTO {

    private String id;
    private String name;
    private Species species;
    private Breed breed;
    private String color;
    private Sex sex;
    private String dateBirth;
    private Integer age;
    private String particularSigns;
    private Origin origin;
    private Customer customer;
    private String createAt;
    private Set<RequestPatientHistoryVaccinations> vaccinations = new LinkedHashSet<>();
    private Set<RequestPhysiologicalConstants> physiologicalConstants = new LinkedHashSet<>();
    private Set<RequestDeworming> dewormingInternal = new LinkedHashSet<>();
    private Set<RequestDeworming> dewormingExternal = new LinkedHashSet<>();
    private RequestFeeding feeding;
    private String previousIllnesses;
    private String surgeries;
    private String familyBackground;
    private Habitat habitat;
    private ReproductiveStatus reproductiveStatus;
    private String allergy;
    private String updateAt;
    private String photo;

}
