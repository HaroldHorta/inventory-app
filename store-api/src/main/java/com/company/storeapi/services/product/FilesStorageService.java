package com.company.storeapi.services.product;

import com.company.storeapi.model.payload.request.product.FileUploadDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesStorageService {

    ResponseProductDTO save(FileUploadDTO file)  throws IOException;

}
