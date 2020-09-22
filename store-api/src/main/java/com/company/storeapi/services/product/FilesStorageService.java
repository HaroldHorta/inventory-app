package com.company.storeapi.services.product;

import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesStorageService {

    ResponseProductDTO save(String id, MultipartFile file)  throws IOException;

}
