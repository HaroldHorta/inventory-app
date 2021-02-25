package com.company.storeapi.repositories.clinicexam.facade;

import com.company.storeapi.model.entity.ClinicExam;

import java.util.List;

public interface ClinicExamRepositoryFacade {

    List<ClinicExam> getAllClinicExam ();

    ClinicExam  validateAndGetClinicExamById (String id);

    ClinicExam saveClinicExam(ClinicExam clinicExam);

    void deleteClinicExam(String id);

}
