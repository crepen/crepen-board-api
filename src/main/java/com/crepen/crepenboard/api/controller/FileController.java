package com.crepen.crepenboard.api.controller;

import com.crepen.crepenboard.api.common.CommonResponse;
import com.crepen.crepenboard.api.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;


    @GetMapping()
    public ResponseEntity<CommonResponse> test() {
        var ss = fileService.findAll();
        System.out.println(ss);

        return new ResponseEntity<>(CommonResponse.success(ss), HttpStatusCode.valueOf(200));
    }


    /**
     * Upload File
     *
     * @param file Upload File Object
     * @return Store File UUID
     */
    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Files.copy(
                file.getInputStream(),
                Paths.get("C:\\Temp\\crepenboard-temp-test").toAbsolutePath().resolve(file.getOriginalFilename()),
                StandardCopyOption.REPLACE_EXISTING
        );

        return new ResponseEntity<>(
                CommonResponse.success(file.getOriginalFilename()),
                HttpStatusCode.valueOf(200)
        );
    }
}
