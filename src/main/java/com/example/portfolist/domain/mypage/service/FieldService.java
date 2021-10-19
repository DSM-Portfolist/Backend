package com.example.portfolist.domain.mypage.service;

import com.example.portfolist.domain.auth.repository.AuthFacade;
import com.example.portfolist.domain.mypage.dto.response.FieldGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final AuthFacade authFacade;

    public List<FieldGetResponse> getField() {
        return authFacade.findFieldKindAll().stream()
                .map(FieldGetResponse::from)
                .collect(Collectors.toList());
    }

}
