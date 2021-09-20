package com.example.portfolist.domain.auth.controller;

import com.example.portfolist.domain.auth.dto.request.EmailCertificationRequest;
import com.example.portfolist.domain.auth.dto.request.GithubUserLoginRequest;
import com.example.portfolist.domain.auth.dto.request.NormalUserLoginRequest;
import com.example.portfolist.domain.auth.dto.response.GithubUserLoginResponse;
import com.example.portfolist.domain.auth.dto.response.NormalUserLoginResponse;
import com.example.portfolist.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ResponseBody
    @PostMapping("/login/normal")
    public NormalUserLoginResponse login(@Valid @RequestBody NormalUserLoginRequest request) {
        return authService.login(request);
    }

    @ResponseBody
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
}
