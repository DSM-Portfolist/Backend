package com.example.portfolist.domain.mypage.service;

import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import com.example.portfolist.domain.mypage.dto.request.PasswordCheckRequest;
import com.example.portfolist.global.file.FileUploadProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final PasswordEncoder passwordEncoder;
    private final FileUploadProvider fileUploadProvider;

    public void changePassword(PasswordChangeRequest request, NormalUser normalUser) {
        if (!passwordEncoder.matches(request.getNowPassword(), normalUser.getPassword())) {
            throw new PasswordNotMatchedException();
        }
        normalUser.setPassword(request.getNewPassword());
    }

    public void checkPassword(PasswordCheckRequest request, NormalUser normalUser) {
        if (!passwordEncoder.matches(request.getNowPassword(), normalUser.getPassword())) {
            throw new PasswordNotMatchedException();
        }
    }

    public void registerProfile(MultipartFile file, NormalUser normalUser) {
        if (normalUser.getUrl() != null) {
            fileUploadProvider.deleteFile(normalUser.getUrl());
        }

        String fileUrl = fileUploadProvider.uploadFile(file);
        normalUser.setUrl(fileUrl);
    }

    public void deleteProfile(NormalUser normalUser) {
        String url = normalUser.getUrl();
        if (url != null) {
            fileUploadProvider.deleteFile(url);
            normalUser.setUrl(null);
        }
    }

}
