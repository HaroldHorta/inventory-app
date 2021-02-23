package com.company.storeapi.model.enums;

public enum ReproductiveStatus {

    CASTRADO(1),
    GESTACION(2),
    ENTERO(3),
    LACTANCIA(4);

    private final Integer id;

    ReproductiveStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
