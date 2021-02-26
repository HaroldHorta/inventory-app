package com.company.storeapi.model.entity;

import com.company.storeapi.model.payload.request.clinichistory.*;
import com.company.storeapi.model.payload.response.diagnosticplan.ResponseDiagnosticPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Document(collection = "clinic_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicHistory {

    @Id
    private String id;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createAt;
    private Veterinary veterinary;
    private Pet pet;
    private RequestPhysiologicalConstants physiologicalConstants;
    private String reasonOfConsultation;
    private String anamnesis;
    private String recipeBook;
    private RequestClinicExamClinicHistory clinicExam;
    private Set<RequestListProblems> listProblems = new LinkedHashSet<>();
    private Set<ResponseDiagnosticPlan> diagnosticPlans = new LinkedHashSet<>();
    private ResultClinic resultClinic;
    private Set<TherapeuticPlan> therapeuticPlan = new LinkedHashSet<>();


}
