package com.company.storeapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum  Role {

    SUPER_ADMINISTRATOR(1),
    ADMINISTRATOR(2),
    SELLER(3),
    VETERINARY(4);

    @Getter
        private final Integer id;
}
