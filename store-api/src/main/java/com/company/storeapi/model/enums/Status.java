package com.company.storeapi.model.enums;

public enum Status {


    ACTIVO(1),
    INACTIVO(2);

    private final Integer id;

    Status(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
