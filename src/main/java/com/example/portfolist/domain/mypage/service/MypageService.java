package com.example.portfolist.domain.mypage.service;

import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import com.example.portfolist.domain.mypage.dto.request.PasswordCheckRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final PasswordEncoder passwordEncoder;

    public void changePassword(PasswordChangeRequest request, NormalUser normalUser) {
        if (!passwordEncoder.matches(request.getNowPassword(), normalUser.getPassword())) {
            throw new PasswordNotMatchedException();
        }
        normalUser.setPassword(request.getNewPassword());
    }

    public void checkPassword(PasswordCheckRequest request, NormalUser normalUser) {
        if (!passwordEncoder.matches(request.getNowPassword(), normalUser.getPassword())) {
            throw new PasswordNotMatchedException();
        }
    }

}
