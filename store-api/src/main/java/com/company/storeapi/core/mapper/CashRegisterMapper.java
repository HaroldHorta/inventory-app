package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.finance.CashRegister;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CashRegisterMapper {

    public abstract ResponseCashRegisterDTO DtoChasRegisterDocument ( CashRegister cashRegister);
}
