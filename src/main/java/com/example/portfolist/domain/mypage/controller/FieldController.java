package com.example.portfolist.domain.mypage.controller;

import com.example.portfolist.domain.mypage.dto.response.FieldGetResponse;
import com.example.portfolist.domain.mypage.service.FieldService;
import com.example.portfolist.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/field/{id}")
    public List<FieldGetResponse> getField(@PathVariable("id") int id) {
        return fieldService.getField(authenticationFacade.getUser());
    }

}
