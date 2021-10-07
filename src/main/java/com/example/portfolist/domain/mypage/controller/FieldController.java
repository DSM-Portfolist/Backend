package com.example.portfolist.domain.mypage.controller;

import com.example.portfolist.domain.mypage.dto.response.FieldGetResponse;
import com.example.portfolist.domain.mypage.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @GetMapping("/field")
    public List<FieldGetResponse> getField() {
        return fieldService.getField();
    }

}
