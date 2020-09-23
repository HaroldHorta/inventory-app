package com.company.storeapi.web.api;

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

    @PostMapping(value = "/upload/{id}")
    public ResponseEntity<ResponseProductDTO> uploadFile(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        ResponseProductDTO created = storageService.save(id,file);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.OK);
    }


   /* @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ProductRestApi.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }*/
}
