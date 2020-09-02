package com.company.storeapi.model.enums;

public enum Status {


    ACTIVE(1),
    INACTIVE(2);

    private final Integer id;

    Status(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
