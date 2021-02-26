package com.company.storeapi.model.payload.response.clinichistory;

import com.company.storeapi.model.entity.*;
import com.company.storeapi.model.payload.request.clinichistory.*;
import com.company.storeapi.model.payload.response.diagnosticplan.ResponseDiagnosticPlan;
import com.company.storeapi.model.payload.response.pet.ResponsePetDTO;
import lombok.Data;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class ResponseClinicHistoryDTO {

    private String id;
    private Date createAt;
    private Veterinary veterinary;
    private ResponsePetDTO pet;
    private String reasonOfConsultation;
    private String anamnesis;
    private String recipeBook;
    private RequestPhysiologicalConstants physiologicalConstants;
    private RequestClinicExamClinicHistory clinicExam;
    private Set<RequestListProblems> listProblems = new LinkedHashSet<>();
    private Set<ResponseDiagnosticPlan> diagnosticPlans = new LinkedHashSet<>();
    private ResultClinic resultClinic;
    private Set<TherapeuticPlan> therapeuticPlan = new LinkedHashSet<>();


}
