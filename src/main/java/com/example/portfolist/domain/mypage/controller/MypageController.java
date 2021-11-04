package com.example.portfolist.domain.mypage.controller;

import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import com.example.portfolist.domain.mypage.dto.request.PasswordCheckRequest;
import com.example.portfolist.domain.mypage.dto.request.UserInfoChangeRequest;
import com.example.portfolist.domain.mypage.dto.response.*;
import com.example.portfolist.domain.mypage.service.MypageService;
import com.example.portfolist.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MypageController {

    private final MypageService mypageService;
    private final AuthenticationFacade authenticationFacade;

    @PatchMapping("/password")
    public void changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        mypageService.changePassword(request, authenticationFacade.getNormalUser());
    }

    @PostMapping("/password")
    public void checkPassword(@Valid @RequestBody PasswordCheckRequest request) {
        mypageService.checkPassword(request, authenticationFacade.getNormalUser());
    }

    @GetMapping("/profile")
    public ProfileGetResponse getProfile() {
        return mypageService.getProfile(authenticationFacade.getUser());
    }

    @PostMapping("/profile")
    public void registerProfile(@RequestPart(value = "file") MultipartFile file) {
        mypageService.registerProfile(file, authenticationFacade.getNormalUser());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/profile")
    public void deleteProfile() {
        mypageService.deleteProfile(authenticationFacade.getNormalUser());
    }

    @GetMapping("/info")
    public UserInfoGetResponse getUserInfo() {
        return mypageService.getUserInfo(authenticationFacade.getUser());
    }

    @PatchMapping("/info")
    public void changeUserInfo(@Valid @RequestBody UserInfoChangeRequest request) {
        mypageService.changeUserInfo(request, authenticationFacade.getUser());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteUser() {
        mypageService.deleteUser(authenticationFacade.getUser());
    }

    @GetMapping("/touching")
    public TouchingPortfolioGetRes.Response getTouchingPortfolio(@RequestParam(value = "page") int page,
                                                                 @RequestParam(value = "size") int size) {
        return mypageService.getTouchingPortfolio(page, size, authenticationFacade.getUser());
    }

    @GetMapping("/portfolio")
    public List<UserPortfolioGetResponse> getUserPortfolio() {
        return mypageService.getUserPortfolio(authenticationFacade.getUser());
    }

    @GetMapping("/notification")
    public List<NotificationGetResponse> getNotification() {
        return mypageService.getNotification(authenticationFacade.getUser());
    }

    @GetMapping("/notification/status")
    public NotificationStatusGetResponse getNotificationStatus() {
        return mypageService.getNotificationStatus(authenticationFacade.getUser());
    }

}
