package com.company.storeapi.model.enums;

import lombok.Getter;

@Getter
public enum TherapeuticPlanOption {

    SUPPORT(1, " Terapia de Sostén"),
    PREVENTIVE(2, " Tratamiento preventivo"),
    SYMPTOMATIC(3, "Tratamiento Sintomático"),
    ETIOLOGICAL(4, "Tratamiento Etiológico");

    private final Integer id;
    private final String description;

    TherapeuticPlanOption(Integer id, String description) {
        this.id = id;
        this.description = description;
    }


}
