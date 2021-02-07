package com.company.storeapi.model.payload.request.clinichistory;

import com.company.storeapi.model.enums.Habitat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestHabitat {

    private Habitat habitat;
    private String description;
}
