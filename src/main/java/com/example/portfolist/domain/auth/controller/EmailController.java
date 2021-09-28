package com.example.portfolist.domain.auth.controller;

import com.example.portfolist.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final AuthService authService;

    @RequestMapping("/receive")
    public String certifyEmailReceive(@RequestParam("token") String token) {
        return "redirect:" + authService.certifyEmail(token);
    }

}