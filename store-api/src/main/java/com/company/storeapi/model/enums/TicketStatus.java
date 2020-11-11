package com.company.storeapi.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum  TicketStatus {

    PAYED(1), CREDIT(2), DEVOLUTION(3);

    @Getter
    private final Integer id;
}
