package com.company.storeapi.model.dto.response.base;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {

    private String code;
    private String description;
}
