package com.example.portfolist.domain.auth.service;

import com.example.portfolist.domain.auth.dto.request.*;
import com.example.portfolist.domain.auth.dto.response.GithubUserLoginResponse;
import com.example.portfolist.domain.auth.dto.response.NormalUserLoginResponse;
import com.example.portfolist.domain.auth.dto.response.TokenRefreshResponse;
import com.example.portfolist.domain.auth.entity.Field;
import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.entity.redis.Certification;
import com.example.portfolist.domain.auth.exception.PasswordNotMatchedException;
import com.example.portfolist.domain.auth.repository.AuthCheckFacade;
import com.example.portfolist.domain.auth.repository.AuthFacade;
import com.example.portfolist.domain.auth.util.api.client.GithubClient;
import com.example.portfolist.global.error.exception.InvalidTokenException;
import com.example.portfolist.global.etc.dto.LocalServerIp;
import com.example.portfolist.global.mail.HtmlSourceProvider;
import com.example.portfolist.global.mail.MailSendProvider;
import com.example.portfolist.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthCheckFacade authCheckFacade;
    private final AuthFacade authFacade;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final LocalServerIp localServerIp;
    private final HtmlSourceProvider htmlSourceProvider;
    private final MailSendProvider mailSendProvider;

    private final GithubClient githubClient;

    @Value("${auth.jwt.refresh}")
    private Long refreshLifespan;

    @Value(value = "${server.port}")
    private String port;

    @Value(value = "${page.success}")
    private String successPage;

    @Value(value = "${page.fail}")
    private String failPage;

    public NormalUserLoginResponse login(NormalUserLoginRequest request) {
        User user = authCheckFacade.findByNormalUserEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(),user.getNormalUser().getPassword())) {
            throw new PasswordNotMatchedException();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getPk());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getPk());
        authFacade.save(refreshToken, refreshLifespan);

        return new NormalUserLoginResponse(accessToken, refreshToken);
    }

    public GithubUserLoginResponse login(GithubUserLoginRequest request) {
        String nickname = githubClient.getUserInfo("token " + request.getGithubToken()).getLogin();

        Optional<User> optionalUser = authFacade.findUserByGithubId(nickname);
        long pk;

        if (optionalUser.isEmpty()) {
            User user = User.builder()
                    .githubId(nickname)
                    .name(nickname)
                    .build();
            user = authFacade.save(user);
            pk = user.getPk();
        }
        else{
            pk = optionalUser.get().getPk();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(pk);
        String refreshToken = jwtTokenProvider.generateRefreshToken(pk);
        authFacade.save(refreshToken, refreshLifespan);

        return new GithubUserLoginResponse(accessToken, refreshToken);

    }

    public void sendEmail(EmailCertificationRequest request) {
        authCheckFacade.checkConflictEmail(request.getEmail());

        String baseurl = localServerIp.getIp() + ":" + port;
        String token = makeToken();
        String content = htmlSourceProvider.makeEmailCertification(baseurl, token);
        mailSendProvider.sendCertification(request.getEmail(), "포트폴리스트 이메일 인증", content);
        authFacade.save(request.getEmail(), token);
    }

    private String makeToken() {
        StringBuilder token = new StringBuilder();
        for (int i=0; i<=3; i++) {
            token.append(Math.random() * 9);
        }
        return String.valueOf(token);
    }

    public String certifyEmail(String token) {
        Optional<Certification> certification = authFacade.findCertificationByToken(token);
        if (certification.isEmpty()) {
            return failPage;
        }
        certification.get().setCertificationTrue();
        return successPage;
    }

    @Transactional
    public void join(NormalUserJoinRequest request) {
        authCheckFacade.checkAuthorizedEmail(request.getEmail());

        NormalUser normalUser = NormalUser.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        normalUser = authFacade.save(normalUser);

        User user = User.builder()
                .normalUser(normalUser)
                .name(request.getName())
                .build();
        user = authFacade.save(user);

        for (int pk : request.getField()) {
            FieldKind fieldKind = authCheckFacade.findByFieldKindId(pk);
            Field field = Field.builder()
                    .user(user)
                    .fieldKind(fieldKind)
                    .build();
            authFacade.save(field);
        }
    }

    public TokenRefreshResponse tokenRefresh(TokenRefreshRequest request) {
        String token = request.getRefreshToken();

        authCheckFacade.checkExistsRefreshToken(token);
        if (!jwtTokenProvider.isRefreshToken(token)) {
            throw new InvalidTokenException();
        }

        Long id = jwtTokenProvider.getId(token);
        String accessToken = jwtTokenProvider.generateAccessToken(id);
        return new TokenRefreshResponse(accessToken);
    }

}
