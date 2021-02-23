package com.company.storeapi.model.payload.request.clinichistory;

import com.company.storeapi.model.enums.FeedingOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestFeeding {

    private FeedingOption feedingOption;
    private String description;

}
