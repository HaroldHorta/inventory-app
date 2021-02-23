package com.company.storeapi.model.payload.request.clinichistory;

import com.company.storeapi.model.enums.ReproductiveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestReproductiveStatus {
    private ReproductiveStatus reproductiveStatus;
}
