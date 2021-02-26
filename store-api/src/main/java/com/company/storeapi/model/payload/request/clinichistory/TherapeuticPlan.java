package com.company.storeapi.model.payload.request.clinichistory;

import lombok.Data;

@Data
public class TherapeuticPlan {

    private String therapeuticPlanOption;
    private String activePrincipleManage;
    private String presentation;
    private String posology;
    private String totalDose;
    private String via;
    private String frequencyDuration;

}
