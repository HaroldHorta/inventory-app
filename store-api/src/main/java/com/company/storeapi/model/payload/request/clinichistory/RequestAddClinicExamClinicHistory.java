package com.company.storeapi.model.payload.request.clinichistory;

import com.company.storeapi.model.enums.OptionClinicExam;
import com.company.storeapi.model.payload.response.clinicexam.ResponseClinicExam;
import lombok.Data;

@Data
public class RequestAddClinicExamClinicHistory {

    private ResponseClinicExam clinicExam;
    private OptionClinicExam optionClinicExam;
    private String observation;


}
