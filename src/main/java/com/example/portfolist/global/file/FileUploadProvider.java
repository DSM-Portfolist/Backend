package com.example.portfolist.global.file;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.portfolist.global.error.exception.WrongFileExtensionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUploadProvider {

    private final AwsS3UploadService s3UploadService;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        InputStream inputStream = file.getInputStream();
        s3UploadService.uploadFile(inputStream, objectMetadata, fileName);
        return s3UploadService.getFileUrl(fileName);
    }

    public void deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));
        s3UploadService.deleteFile(fileName);
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new WrongFileExtensionException();
        }
    }

}
