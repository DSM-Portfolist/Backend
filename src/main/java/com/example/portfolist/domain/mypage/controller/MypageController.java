package com.example.portfolist.domain.mypage.controller;

import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import com.example.portfolist.domain.mypage.dto.request.PasswordCheckRequest;
import com.example.portfolist.domain.mypage.dto.request.UserInfoChangeRequest;
import com.example.portfolist.domain.mypage.dto.response.UserInfoGetResponse;
import com.example.portfolist.domain.mypage.service.MypageService;
import com.example.portfolist.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
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

    @PostMapping("/profile")
    public void registerProfile(@RequestPart(value = "file", required = false) MultipartFile file) {
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

    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/info")
    public void changeUserInfo(@RequestBody UserInfoChangeRequest request) {
        mypageService.changeUserInfo(request, authenticationFacade.getUser());
    }
}
