package com.company.storeapi.model.enums;

public enum BodyCondition {

    CAQUÉCTICO(1),
    DELGADO(2),
    NORMAL(3),
    OBESO(4),
    SOBREPESO(5);

    private final Integer id;

    BodyCondition(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
