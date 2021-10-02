package com.example.portfolist.domain.mypage.service;

import com.amazonaws.util.CollectionUtils;
import com.example.portfolist.domain.auth.entity.Field;
import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.auth.repository.AuthCheckFacade;
import com.example.portfolist.domain.auth.repository.AuthFacade;
import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import com.example.portfolist.domain.mypage.dto.request.PasswordCheckRequest;
import com.example.portfolist.domain.mypage.dto.request.UserInfoChangeRequest;
import com.example.portfolist.domain.mypage.dto.response.UserInfoGetResponse;
import com.example.portfolist.global.file.FileUploadProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final PasswordEncoder passwordEncoder;
    private final FileUploadProvider fileUploadProvider;

    private final AuthFacade authFacade;
    private final AuthCheckFacade authCheckFacade;

    public void changePassword(PasswordChangeRequest request, NormalUser normalUser) {
        if (!passwordEncoder.matches(request.getNowPassword(), normalUser.getPassword())) {
            throw new PasswordNotMatchedException();
        }
        normalUser.changePassword(passwordEncoder.encode(request.getNewPassword()));
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
        normalUser.updateUrl(fileUrl);
    }

    @Async
    public void deleteProfile(NormalUser normalUser) {
        String url = normalUser.getUrl();
        if (url != null) {
            fileUploadProvider.deleteFile(url);
            normalUser.updateUrl(null);
        }
    }

    public UserInfoGetResponse getUserInfo(User user) {
        return UserInfoGetResponse.from(user);
    }

    @Async
    public void changeUserInfo(UserInfoChangeRequest request, User user) {
        user.updateUserInfo(request.getName(), request.getIntroduce());

        if (!CollectionUtils.isNullOrEmpty(request.getField())) {
            List<Integer> changeField = request.getField();
            List<Integer> nowField = authFacade.findFieldByUser(user).stream()
                    .map(field -> {
                        return field.getFieldKind().getPk();
                    }).collect(Collectors.toList());

            List<Field> deleteField = new ArrayList<>();
            for(int fieldId : nowField) {
                if (!changeField.contains(fieldId)) {
                    Field field = authCheckFacade.findFieldByFieldKindPk(fieldId);
                    deleteField.add(field);
                }
            }

            List<Field> saveField = new ArrayList<>();
            for(int fieldId : changeField) {
                if (!nowField.contains(fieldId)) {
                    FieldKind fieldKind = authCheckFacade.findFieldKindById(fieldId);
                    Field field = Field.builder()
                            .user(user)
                            .fieldKind(fieldKind)
                            .build();
                    deleteField.add(field);
                }
            }

            authFacade.delete(deleteField);
            authFacade.save(saveField);
        }
    }
}
