package com.company.storeapi.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaymentType {
  CASH(1),
  CREDIT(2),
  TRANSACTION(3);

@Getter
private final int id;

}
