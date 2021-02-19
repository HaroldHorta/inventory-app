package com.company.storeapi.model.enums;

public enum Attitude {

    ASTÉNICO(1),
    APOPLÉTICO(2),
    LINFÁTICO(3);

    private final Integer id;

    Attitude(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
