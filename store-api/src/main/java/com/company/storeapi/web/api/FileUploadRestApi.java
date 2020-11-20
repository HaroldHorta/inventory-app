package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.product.FileUploadDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import com.company.storeapi.services.product.FilesStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/file")
@CrossOrigin({"*"})
public class FileUploadRestApi {

    private final FilesStorageService storageService;

    public FileUploadRestApi(FilesStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseProductDTO> uploadFile(@RequestBody FileUploadDTO file) throws IOException {
        ResponseProductDTO created = storageService.save(file);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }
}
