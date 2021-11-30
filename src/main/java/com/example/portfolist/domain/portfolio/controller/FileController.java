package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.domain.portfolio.dto.response.FileNameResponse;
import com.example.portfolist.domain.portfolio.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    public FileNameResponse uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @PostMapping("/pdf")
    @ResponseStatus(HttpStatus.CREATED)
    public FileNameResponse uploadPDF(@RequestPart(value = "file") MultipartFile file) {
        return fileService.uploadPDF(file);
    }
}
