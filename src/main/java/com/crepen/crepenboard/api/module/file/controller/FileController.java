package com.crepen.crepenboard.api.module.file.controller;

import com.crepen.crepenboard.api.common.system.model.BaseResponse;
import com.crepen.crepenboard.api.module.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Tag(name = "File Controller" , description = "File Control")
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;


    @Operation(summary = "Test")
    @GetMapping()
    public ResponseEntity<BaseResponse> test() {
        var ss = fileService.findAll();
        System.out.println(ss);

        return new ResponseEntity<>(BaseResponse.success(ss), HttpStatusCode.valueOf(200));
    }


    /**
     * Upload File
     *
     * @param file Upload File Object
     * @return Store File UUID
     */
    @PostMapping("/upload")
    public ResponseEntity<BaseResponse> uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Files.copy(
                file.getInputStream(),
                Paths.get("C:\\Temp\\crepenboard-temp-test").toAbsolutePath().resolve(file.getOriginalFilename()),
                StandardCopyOption.REPLACE_EXISTING
        );

        return new ResponseEntity<>(
                BaseResponse.success(file.getOriginalFilename()),
                HttpStatusCode.valueOf(200)
        );
    }
}
