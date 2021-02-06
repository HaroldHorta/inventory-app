package com.company.storeapi.model.enums;

public enum FeedingOption {

    BALANCEADA(1),
    CASERA(2),
    MIXTA(3),
    OTRA(4);

    private final Integer id;

    FeedingOption(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
