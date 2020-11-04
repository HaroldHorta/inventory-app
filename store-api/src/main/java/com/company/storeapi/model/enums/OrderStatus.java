package com.company.storeapi.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {
    OPEN(1), PAYED(3), CANCELLED(4);

    @Getter
    private final Integer id;

}
