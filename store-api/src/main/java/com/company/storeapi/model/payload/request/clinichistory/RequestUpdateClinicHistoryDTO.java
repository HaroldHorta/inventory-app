package com.company.storeapi.model.payload.request.clinichistory;

import com.company.storeapi.model.enums.ReproductiveStatus;
import lombok.Data;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class RequestUpdateClinicHistoryDTO {

    private String id;
    private Date createAt;
    private String veterinary;
    private String customer;
    private String reasonOfConsultation;
    private String anamnesis;
    Set<RequestVaccination> vaccinations = new LinkedHashSet<>();
    private RequestFeeding feeding;
    private ReproductiveStatus reproductiveStatus;
    private String previousIllnesses;
    private String surgeries;
    private String familyBackground;
    private RequestPhysiologicalConstants physiologicalConstants;
}
