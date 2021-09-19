package com.example.portfolist.domain.auth.controller;

import com.example.portfolist.domain.auth.dto.request.EmailCertificationRequest;
import com.example.portfolist.domain.auth.dto.request.GithubUserLoginRequest;
import com.example.portfolist.domain.auth.dto.request.NormalUserLoginRequest;
import com.example.portfolist.domain.auth.dto.response.GithubUserLoginResponse;
import com.example.portfolist.domain.auth.dto.response.NormalUserLoginResponse;
import com.example.portfolist.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/normal")
    public NormalUserLoginResponse login(@Valid @RequestBody NormalUserLoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/login/github")
    public GithubUserLoginResponse login(@Valid @RequestBody GithubUserLoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/email")
    public void certifyEmail(@Valid @RequestBody EmailCertificationRequest request) {
        authService.certifyEmail(request);
    }

    @RequestMapping("/receive")
    public void certifyEmailReceive(@RequestParam("token") String token) {

    }
}
