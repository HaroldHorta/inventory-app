package com.company.storeapi.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum  TypeDocument {

    CC(1), TI(2), CE(3), PASSPORT(4);

    @Getter
    private final Integer id;
}
