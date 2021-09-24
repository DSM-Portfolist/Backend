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
import com.example.portfolist.domain.auth.repository.AuthFacade;
import com.example.portfolist.domain.auth.repository.repository.FieldRepository;
import com.example.portfolist.domain.auth.repository.repository.NormalUserRepository;
import com.example.portfolist.domain.auth.repository.repository.UserRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.CertificationRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.RefreshTokenRepository;
import com.example.portfolist.domain.auth.util.api.client.GithubClient;
import com.example.portfolist.global.error.exception.InvalidTokenException;
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

    private final AuthFacade authFacade;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final String localServerIp;
    private final HtmlSourceProvider htmlSourceProvider;
    private final MailSendProvider mailSendProvider;

    private final CertificationRepository certificationRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final NormalUserRepository normalUserRepository;
    private final UserRepository userRepository;
    private final FieldRepository fieldRepository;

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
        User user = authFacade.findByNormalUserEmail(request.getEmail());
        if(!passwordEncoder.matches(request.getPassword(),user.getNormalUser().getPassword())) {
            throw new PasswordNotMatchedException();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getPk());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getPk());
        refreshTokenRepository.saveRefreshToken(refreshToken, refreshLifespan);

        return new NormalUserLoginResponse(accessToken, refreshToken);
    }

    public GithubUserLoginResponse login(GithubUserLoginRequest request) {
        String nickname = githubClient.getUserInfo("token " + request.getGithubToken()).getLogin();

        Optional<User> optionalUser = userRepository.findByGithubId(nickname);
        long pk;

        if(optionalUser.isEmpty()) {
            User user = User.builder()
                    .githubId(nickname)
                    .name(nickname)
                    .build();
            user = userRepository.save(user);
            pk = user.getPk();
        }
        else{
            pk = optionalUser.get().getPk();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(pk);
        String refreshToken = jwtTokenProvider.generateRefreshToken(pk);
        refreshTokenRepository.saveRefreshToken(refreshToken, refreshLifespan);

        return new GithubUserLoginResponse(accessToken, refreshToken);

    }

    public void sendEmail(EmailCertificationRequest request) {
        authFacade.checkConflictEmail(request.getEmail());

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

    @Transactional
    public void join(NormalUserJoinRequest request) {
        authFacade.checkAuthorizedEmail(request.getEmail());

        NormalUser normalUser = NormalUser.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        normalUser = normalUserRepository.save(normalUser);

        User user = User.builder()
                .normalUser(normalUser)
                .name(request.getName())
                .build();
        user = userRepository.save(user);

        for(int pk : request.getField()) {
            FieldKind fieldKind = authFacade.findByFieldKindId(pk);
            Field field = Field.builder()
                    .user(user)
                    .fieldKind(fieldKind)
                    .build();
            fieldRepository.save(field);
        }
    }

    public TokenRefreshResponse tokenRefresh(TokenRefreshRequest request) {
        String token = request.getRefreshToken();

        authFacade.checkExistsRefreshToken(token);
        if(!jwtTokenProvider.isRefreshToken(token)) {
            throw new InvalidTokenException();
        }

        Long id = jwtTokenProvider.getId(token);
        String accessToken = jwtTokenProvider.generateAccessToken(id);
        return new TokenRefreshResponse(accessToken);
    }

}
