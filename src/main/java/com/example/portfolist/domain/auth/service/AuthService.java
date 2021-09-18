package com.example.portfolist.domain.auth.service;

import com.example.portfolist.domain.auth.dto.request.NormalUserLoginRequest;
import com.example.portfolist.domain.auth.dto.response.NormalUserLoginResponse;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.auth.repository.UserFacade;
import com.example.portfolist.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public NormalUserLoginResponse login(NormalUserLoginRequest request) {
        User user = userFacade.findByNormalUserEmail(request.getEmail());
        if(!passwordEncoder.matches(request.getPassword(),user.getNormalUser().getPassword())) {
            throw new PasswordNotMatchedException();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getPk());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getPk());

        return new NormalUserLoginResponse(accessToken, refreshToken);
    }

}
