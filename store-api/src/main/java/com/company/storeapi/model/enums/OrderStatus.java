package com.company.storeapi.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {
    ABIERTA(1), PAGADA(3), CANCELADA(4);

    @Getter
    private final Integer id;

}
