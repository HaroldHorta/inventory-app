package com.company.storeapi.model.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;

public class RequestAddCategoryDTO {

    @Schema(example = "5f4143c5498c761de44e7426")
    private String id;
    @Schema(example = "Muebles")
    private String description;


    public RequestAddCategoryDTO(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public RequestAddCategoryDTO() {
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
