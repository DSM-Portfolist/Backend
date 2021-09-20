package com.example.portfolist.domain.auth.service;

import com.example.portfolist.domain.auth.dto.request.EmailCertificationRequest;
import com.example.portfolist.domain.auth.dto.request.GithubUserLoginRequest;
import com.example.portfolist.domain.auth.dto.request.NormalUserLoginRequest;
import com.example.portfolist.domain.auth.dto.response.GithubUserLoginResponse;
import com.example.portfolist.domain.auth.dto.response.NormalUserLoginResponse;
import com.example.portfolist.domain.auth.entity.Certification;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.auth.repository.UserFacade;
import com.example.portfolist.domain.auth.repository.repository.CertificationRepository;
import com.example.portfolist.global.mail.HtmlSourceProvider;
import com.example.portfolist.global.mail.MailSendProvider;
import com.example.portfolist.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final String localServerIp;
    private final HtmlSourceProvider htmlSourceProvider;
    private final MailSendProvider mailSendProvider;
    private final CertificationRepository certificationRepository;

    @Value(value = "${server.port}")
    private String port;

    @Value(value = "${page.success}")
    private String successPage;

    @Value(value = "${page.fail}")
    private String failPage;

    public NormalUserLoginResponse login(NormalUserLoginRequest request) {
        User user = userFacade.findByNormalUserEmail(request.getEmail());
        if(!passwordEncoder.matches(request.getPassword(),user.getNormalUser().getPassword())) {
            throw new PasswordNotMatchedException();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getPk());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getPk());

        return new NormalUserLoginResponse(accessToken, refreshToken);
    }

    public GithubUserLoginResponse login(GithubUserLoginRequest request) {

        return null; // TODO openfeign 공부 후 개발 예정

    }

    public void sendEmail(EmailCertificationRequest request) {
        String baseurl = localServerIp + ":" + port;
        String token = makeToken();
        String content = htmlSourceProvider.makeEmailCertification(baseurl, token);
        mailSendProvider.sendCertification(request.getEmail(), "포트폴리스트 이메일 인증", content);
        Certification certification = Certification.builder()
                .email(request.getEmail())
                .token(token)
                .certification(false)
                .exp(300000L)
                .build();
        certificationRepository.save(certification);
    }

    private String makeToken() {
        StringBuilder token = new StringBuilder();
        for(int i=0; i<=3; i++) {
            token.append(Math.random() * 9);
        }
        return String.valueOf(token);
    }

    public String certifyEmail(String token) {
        Optional<Certification> certification = certificationRepository.findByToken(token);
        if(certification.isEmpty()) {
            return failPage;
        }
        certification.get().setCertificationTrue();
        return successPage;
    }

}
