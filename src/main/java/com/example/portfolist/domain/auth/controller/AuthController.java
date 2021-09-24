package com.example.portfolist.domain.auth.controller;

import com.example.portfolist.domain.auth.dto.request.*;
import com.example.portfolist.domain.auth.dto.response.GithubUserLoginResponse;
import com.example.portfolist.domain.auth.dto.response.NormalUserLoginResponse;
import com.example.portfolist.domain.auth.dto.response.TokenRefreshResponse;
import com.example.portfolist.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/login/normal")
    public NormalUserLoginResponse login(@Valid @RequestBody NormalUserLoginRequest request) {
        return authService.login(request);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/login/github")
    public GithubUserLoginResponse login(@Valid @RequestBody GithubUserLoginRequest request) {
        return authService.login(request);
    }

    @ResponseBody
    @PostMapping("/email")
    public void certifyEmail(@Valid @RequestBody EmailCertificationRequest request) {
        authService.sendEmail(request);
    }

    @RequestMapping("/receive")
    public String certifyEmailReceive(@RequestParam("token") String token) {
        return "redirect:" + authService.certifyEmail(token);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join")
    public void join(@Valid @RequestBody NormalUserJoinRequest request) {
        authService.join(request);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/token-refresh")
    public TokenRefreshResponse tokenRefresh(@Valid @RequestBody TokenRefreshRequest request) {
        return authService.tokenRefresh(request);
    }
}
