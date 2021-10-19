package com.example.portfolist.domain.auth.controller;

import com.example.portfolist.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final AuthService authService;

    @RequestMapping("/receive")
    public RedirectView certifyEmailReceive(@RequestParam("token") String token) {
        return new RedirectView(authService.certifyEmail(token));
    }

}