package com.company.storeapi.model.enums;

public enum Option {
    SI(1),
    NO(2);

    private final Integer id;

    Option(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
