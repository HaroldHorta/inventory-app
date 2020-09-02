package com.company.storeapi.model.enums;

public enum TypeMovement {


    BUY(1),
    SELL(2);

    private final Integer id;

    TypeMovement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
