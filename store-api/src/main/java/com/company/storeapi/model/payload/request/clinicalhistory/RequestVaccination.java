package com.company.storeapi.model.payload.request.clinicalhistory;

import com.company.storeapi.model.entity.Vaccination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestVaccination {
    private Vaccination description;
    private Date vaccinationDate;
}
