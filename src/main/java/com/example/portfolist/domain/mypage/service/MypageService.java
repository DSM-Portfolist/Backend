package com.example.portfolist.domain.mypage.service;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.mypage.dto.request.PasswordChangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final PasswordEncoder passwordEncoder;

    public void changePassword(PasswordChangeRequest request, User user) {
        if(!passwordEncoder.matches(request.getNowPassword(),user.getNormalUser().getPassword())) {
            throw new PasswordNotMatchedException();
        }
        user.getNormalUser().setPassword(request.getNewPassword());
    }

}
