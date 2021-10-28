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
import com.example.portfolist.domain.auth.util.api.dto.GithubResponse;
import com.example.portfolist.global.error.exception.InvalidTokenException;
import com.example.portfolist.global.event.GlobalEventPublisher;
import com.example.portfolist.global.mail.HtmlSourceProvider;
import com.example.portfolist.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthCheckFacade authCheckFacade;
    private final AuthFacade authFacade;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final HtmlSourceProvider htmlSourceProvider;
    private final GlobalEventPublisher globalEventPublisher;

    private final GithubClient githubClient;

    @Value("${auth.jwt.refresh}")
    private Long refreshLifespan;

    @Value(value = "${server.port}")
    private String port;

    @Value(value = "${link.domain}")
    private String domain;

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
        GithubResponse response = githubClient.getUserInfo("token " + request.getGithubToken());
        String nickname = response.getLogin();
        String name = response.getName();
        String url = response.getAvatarUrl();

        Optional<User> optionalUser = authFacade.findUserByGithubId(nickname);
        long pk;

        if (optionalUser.isEmpty()) {
            User user = User.builder()
                    .githubId(nickname)
                    .name(name==null ? nickname : name)
                    .url(url)
                    .build();
            user = authFacade.save(user);
            pk = user.getPk();
        }
        else{
            User user = optionalUser.get();
            pk = user.getPk();
            user.updateChange(name, url);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(pk);
        String refreshToken = jwtTokenProvider.generateRefreshToken(pk);
        authFacade.save(refreshToken, refreshLifespan);

        return new GithubUserLoginResponse(accessToken, refreshToken);

    }

    public void sendEmail(EmailCertificationRequest request) {
        authCheckFacade.checkConflictEmail(request.getEmail());

        String baseurl = domain + ":" + port;
        String token = makeToken();
        String content = htmlSourceProvider.makeEmailCertification(baseurl, token);
        globalEventPublisher.sendEmail(request.getEmail(), "포트폴리스트 이메일 인증", content);
        authFacade.save(request.getEmail(), token);
    }

    private String makeToken() {
        StringBuilder token = new StringBuilder();
        for (int i=0; i<=3; i++) {
            token.append(Math.round(Math.random() * 9));
        }
        return String.valueOf(token);
    }

    public String certifyEmail(String token) {
        Optional<Certification> certification = authFacade.findCertificationByToken(token);
        if (certification.isEmpty()) {
            return failPage;
        }
        authFacade.changeCertification(certification.get());
        return successPage;
    }

    public void join(NormalUserJoinRequest request) {
        authCheckFacade.checkAuthorizedEmail(request.getEmail());

        NormalUser normalUser = NormalUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        normalUser = authFacade.save(normalUser);

        User user = User.builder()
                .normalUser(normalUser)
                .name(request.getName())
                .build();
        user = authFacade.save(user);

        List<Field> fields = new ArrayList<>();
        for (int pk : request.getField()) {
            FieldKind fieldKind = authCheckFacade.findFieldKindById(pk);
            Field field = Field.builder()
                    .user(user)
                    .fieldKind(fieldKind)
                    .build();
            fields.add(field);
        }

        authFacade.save(fields);
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
