package com.example.portfolist.domain.mypage.controller;

import com.example.portfolist.domain.mypage.dto.response.InfoUserGetResponse;
import com.example.portfolist.domain.mypage.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {

    private final InfoService infoService;

    @GetMapping("/user/{userId}")
    public InfoUserGetResponse getUserInfo(@PathVariable long userId) {
        return infoService.getUserInfo(userId);
    }

}
