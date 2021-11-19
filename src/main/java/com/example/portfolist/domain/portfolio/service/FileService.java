package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.portfolio.dto.response.FileNameResponse;
import com.example.portfolist.global.file.FileUploadProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileUploadProvider fileUploadProvider;

    public FileNameResponse uploadFile(MultipartFile file) {
        return new FileNameResponse(fileUploadProvider.uploadFile(file));
    }
}
