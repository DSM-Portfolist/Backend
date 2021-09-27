package com.example.portfolist.domain.mypage.controller;

import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import com.example.portfolist.domain.mypage.service.MypageService;
import com.example.portfolist.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class MypageController {

    private final MypageService mypageService;
    private final AuthenticationFacade authenticationFacade;

    @PatchMapping("/password")
    public void changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        mypageService.changePassword(request, authenticationFacade.getUser());
    }

}
