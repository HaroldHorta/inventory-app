package com.company.storeapi.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaymentType {
  EFECTIVO(1),
  CREDITO(2),
  TRANSACCION(3);

@Getter
private final int id;

}
