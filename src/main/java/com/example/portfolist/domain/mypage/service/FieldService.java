package com.example.portfolist.domain.mypage.service;

import com.example.portfolist.domain.auth.entity.Field;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.mypage.dto.response.FieldGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldService {

    public List<FieldGetResponse> getField(User user) {
        List<Field> fields = user.getFieldList();
        return fields.stream()
                .map(FieldGetResponse::from)
                .collect(Collectors.toList());
    }

}
