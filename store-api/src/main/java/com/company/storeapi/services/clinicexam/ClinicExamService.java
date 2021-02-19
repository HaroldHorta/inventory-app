package com.company.storeapi.services.clinicexam;

import com.company.storeapi.model.payload.request.clinicaexam.RequestAddClinicExam;
import com.company.storeapi.model.payload.response.clinicexam.ResponseClinicExam;

import java.util.List;

public interface ClinicExamService {

    List<ResponseClinicExam> getAllClinicExam ();

    ResponseClinicExam  validateAndGetClinicExamById (String id);

    ResponseClinicExam saveClinicExam(RequestAddClinicExam requestAddClinicExam);

    ResponseClinicExam updateClinicExam(String id, RequestAddClinicExam requestAddClinicExam);

    void deleteClinicExam(String id);

}
