package com.company.storeapi.model.enums;

public enum Habitat {

    CASA(1),
    LOTE(2),
    FINCA(3),
    TALLER(3),
    OTRO(4);

    private final Integer id;

    Habitat(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
