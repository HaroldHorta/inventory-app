package com.company.storeapi.model.enums;

public enum Origin {

    URBANO(1),
    RURAL(2);

    private final Integer id;

    Origin(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
