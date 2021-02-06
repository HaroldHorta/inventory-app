package com.company.storeapi.model.enums;

public enum Sex {

    FEMENINO(1),
    MASCULINO(2);

    private final Integer id;

    Sex(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
