package com.company.storeapi.model.payload.response.pet;

import com.company.storeapi.model.entity.Breed;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.entity.Species;
import com.company.storeapi.model.enums.Origin;
import com.company.storeapi.model.enums.ReproductiveStatus;
import com.company.storeapi.model.enums.Sex;
import com.company.storeapi.model.payload.request.clinichistory.RequestFeeding;
import com.company.storeapi.model.payload.request.clinichistory.RequestHabitat;
import com.company.storeapi.model.payload.request.clinichistory.RequestPhysiologicalConstants;
import com.company.storeapi.model.payload.request.pet.RequestPatientHistoryDeworming;
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
    private Set<RequestPatientHistoryDeworming> dewormingInternal = new LinkedHashSet<>();
    private Set<RequestPatientHistoryDeworming> dewormingExternal = new LinkedHashSet<>();
    private RequestFeeding feeding;
    private String previousIllnesses;
    private String surgeries;
    private String familyBackground;
    private RequestHabitat habitat;
    private ReproductiveStatus reproductiveStatus;
    private String allergy;
    private String updateAt;
    private String photo;

}
