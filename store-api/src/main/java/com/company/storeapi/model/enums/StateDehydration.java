package com.company.storeapi.model.enums;

import lombok.Getter;

public enum StateDehydration {

    NORMAL(1, "NORMAL"),
    CEROACINCO(2, "0-5%"),
    SEISASIETE(3, "6-7%"),
    OCHOANUEVE(4, "8-9%"),
    MAS10(5, "+10%");

    @Getter
    private final Integer id;
    @Getter
    private final String description;

    StateDehydration(Integer id, String description) {
        this.id = id;
        this.description = description;
    }




}
