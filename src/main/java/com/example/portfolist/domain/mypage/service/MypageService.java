package com.example.portfolist.domain.mypage.service;

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
import com.example.portfolist.domain.mypage.dto.response.*;
import com.example.portfolist.domain.mypage.repository.MypageFacade;
import com.example.portfolist.domain.portfolio.repository.PortfolioFacade;
import com.example.portfolist.global.error.exception.WrongFileException;
import com.example.portfolist.global.file.FileUploadProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
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
    private final MypageFacade mypageFacade;
    private final PortfolioFacade portfolioFacade;

    public void changePassword(PasswordChangeRequest request, NormalUser normalUser) {
        if (!passwordEncoder.matches(request.getNowPassword(), normalUser.getPassword())) {
            throw new PasswordNotMatchedException();
        }
        normalUser.changePassword(passwordEncoder.encode(request.getNewPassword()));
        authFacade.save(normalUser);
    }

    public void checkPassword(PasswordCheckRequest request, NormalUser normalUser) {
        if (!passwordEncoder.matches(request.getNowPassword(), normalUser.getPassword())) {
            throw new PasswordNotMatchedException();
        }
    }

    public void registerProfile(MultipartFile file, NormalUser normalUser) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String contentType = file.getContentType();
        if (!"png".equals(extension) && !"jpeg".equals(extension) && !"jpg".equals(extension)
                || contentType == null || !contentType.split("/")[0].equals("image")) {
            throw new WrongFileException();
        }

        User user = normalUser.getUser();
        if (user.getUrl() != null) {
            fileUploadProvider.deleteFile(user.getUrl());
        }

        String fileUrl = fileUploadProvider.uploadFile(file);
        user.updateProfile(fileUrl);
        authFacade.save(user);
    }

    public void deleteProfile(NormalUser normalUser) {
        User user = normalUser.getUser();
        String url = user.getUrl();
        if (url != null) {
            fileUploadProvider.deleteFile(url);
            user.updateProfile(null);
            authFacade.save(user);
        }
    }

    public UserInfoGetResponse getUserInfo(User user) {
        return UserInfoGetResponse.from(user);
    }

    public void changeUserInfo(UserInfoChangeRequest request, User user) {
        user.updateUserInfo(request.getName(), request.getIntroduce());

        List<Integer> changeField = request.getField();
        List<Integer> nowField = authFacade.findFieldByUser(user).stream()
                .map(field -> field.getFieldKind().getId()).collect(Collectors.toList());

        List<Field> deleteField = new ArrayList<>();
        for(int fieldId : nowField) {
            if (!changeField.contains(fieldId)) {
                Field field = authCheckFacade.findFieldByFieldKindIdAndUser(fieldId, user);
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
                saveField.add(field);
            }
        }

        authFacade.delete(deleteField);
        authFacade.save(saveField);
    }

    public void deleteUser(User user) {
        try {
            authFacade.deleteNormalByUser(user);
        } catch (Exception e) {
            authFacade.deleteUser(user);
        }
    }

    public TouchingPortfolioGetRes.Response getTouchingPortfolio(int page, int size, User user) {
        return TouchingPortfolioGetRes.Response.from(portfolioFacade.findTouchingAll(page, size, user));
    }

    public List<UserPortfolioGetResponse> getUserPortfolio(User user) {
        return user.getPortfolioList().stream()
                .map(UserPortfolioGetResponse::from)
                .collect(Collectors.toList());
    }

    public List<NotificationGetResponse> getNotification(User user) {
        mypageFacade.deleteAlreadyReadNotification(user);

        return mypageFacade.findNotificationByUser(user).stream()
                .map(NotificationGetResponse::from)
                .collect(Collectors.toList());
    }

    public NotificationStatusGetResponse getNotificationStatus(User user) {
        return new NotificationStatusGetResponse(user.isNotificationStatus());
    }

    public void changeNotificationStatus(User user, boolean status) {
        user.updateNotificationStatus(status);
        authFacade.save(user);
    }

}
