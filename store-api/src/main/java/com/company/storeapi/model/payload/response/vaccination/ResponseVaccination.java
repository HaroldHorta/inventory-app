package com.company.storeapi.model.payload.response.vaccination;

import com.company.storeapi.model.entity.Vaccination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVaccination {
    private Vaccination vaccination;
    private String vaccinationDate;
}
