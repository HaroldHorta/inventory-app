package com.company.storeapi.model.payload.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDTO {

    private String idProduct;
    private  String dataPhoto;

}
