package com.company.storeapi.model.enums;

public enum Sex {

    HEMBRA(1),
    MACHO(2);

    private final Integer id;

    Sex(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
