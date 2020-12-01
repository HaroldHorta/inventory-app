package com.company.storeapi.core.mapper;


import com.company.storeapi.model.entity.finance.Expenses;
import com.company.storeapi.model.payload.response.finance.ResponseExpensesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class ExpensesMapper {

    public abstract ResponseExpensesDTO DtoResponseExpenses(Expenses expenses);

}
