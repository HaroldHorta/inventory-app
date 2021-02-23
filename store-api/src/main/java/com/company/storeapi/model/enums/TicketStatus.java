package com.company.storeapi.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum  TicketStatus {

    PAGADA(1), CREDITO(2), DEVOLUCION(3);

    @Getter
    private final Integer id;
}
