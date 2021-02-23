package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.DiagnosticPlan;
import com.company.storeapi.model.payload.response.diagnosticplan.ResponseDiagnosticPlan;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DiagnosticPlanMapper {

    ResponseDiagnosticPlan toDiagnosticPlanDto(DiagnosticPlan diagnosticPlan);
}
