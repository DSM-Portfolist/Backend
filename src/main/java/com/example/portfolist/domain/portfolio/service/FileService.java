package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.portfolio.dto.response.FileNameResponse;
import com.example.portfolist.global.error.exception.WrongFileException;
import com.example.portfolist.global.file.FileUploadProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileUploadProvider fileUploadProvider;

    public FileNameResponse uploadFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"png".equals(extension) && !"jpeg".equals(extension) && !"jpg".equals(extension)) {
            throw new WrongFileException();
        }

        return new FileNameResponse(fileUploadProvider.uploadFile(file));
    }

    public FileNameResponse uploadPDF(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"pdf".equals(extension)) {
            throw new WrongFileException();
        }

        return new FileNameResponse(fileUploadProvider.uploadFile(file));
    }
}
