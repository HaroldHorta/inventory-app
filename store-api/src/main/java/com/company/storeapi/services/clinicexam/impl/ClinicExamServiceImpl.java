package com.company.storeapi.services.clinicexam.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.mapper.ClinicExamMapper;
import com.company.storeapi.model.entity.ClinicExam;
import com.company.storeapi.model.payload.request.clinicaexam.RequestAddClinicExam;
import com.company.storeapi.model.payload.response.clinicexam.ResponseClinicExam;
import com.company.storeapi.repositories.clinicexam.facade.ClinicExamRepositoryFacade;
import com.company.storeapi.services.clinicexam.ClinicExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicExamServiceImpl implements ClinicExamService {

    private final ClinicExamRepositoryFacade clinicExamRepositoryFacade;
    private final ClinicExamMapper clinicExamMapper;

    @Override
    public List<ResponseClinicExam> getAllClinicExam() {
        List<ClinicExam> clinicExams = clinicExamRepositoryFacade.getAllClinicExam();
        return clinicExams.stream().map(clinicExamMapper::toClinicExamDto).collect(Collectors.toList());
    }

    @Override
    public ResponseClinicExam validateAndGetClinicExamById(String id) {
        return clinicExamMapper.toClinicExamDto(clinicExamRepositoryFacade.validateAndGetClinicExamById(id));
    }

    @Override
    public ResponseClinicExam saveClinicExam(RequestAddClinicExam requestAddClinicExam) {

        if (requestAddClinicExam.getExam().isEmpty()) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "el campo no pueden estar vacia");
        }
        ClinicExam clinicExam = new ClinicExam();
        clinicExam.setExam(requestAddClinicExam.getExam());
        clinicExam.setCreatAt(new Date());

        return clinicExamMapper.toClinicExamDto(clinicExamRepositoryFacade.saveClinicExam(clinicExam));
    }

    @Override
    public void deleteClinicExam(String id) {
        clinicExamRepositoryFacade.deleteClinicExam(id);
    }

}
