package com.example.portfolist.domain.mypage.service;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.repository.AuthFacade;
import com.example.portfolist.domain.mypage.dto.response.InfoUserGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoService {

    private final AuthFacade authFacade;

    public InfoUserGetResponse getUserInfo(long userId) {
        User user = authFacade.findUserById(userId);
        return InfoUserGetResponse.from(user);
    }

}
