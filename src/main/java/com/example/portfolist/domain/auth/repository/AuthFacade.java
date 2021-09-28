package com.example.portfolist.domain.auth.repository;

import com.example.portfolist.domain.auth.entity.Field;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.entity.redis.Certification;
import com.example.portfolist.domain.auth.entity.redis.RefreshToken;
import com.example.portfolist.domain.auth.repository.repository.FieldKindRepository;
import com.example.portfolist.domain.auth.repository.repository.FieldRepository;
import com.example.portfolist.domain.auth.repository.repository.NormalUserRepository;
import com.example.portfolist.domain.auth.repository.repository.UserRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.CertificationRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final CertificationRepository certificationRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;
    private final NormalUserRepository normalUserRepository;
    private final FieldRepository fieldRepository;


    public void save(String token, Long refreshLifespan) {
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .exp(refreshLifespan)
                .build();
        refreshTokenRepository.save(refreshToken);
    }

    public Optional<User> findUserByGithubId(String id) {
        return userRepository.findByGithubId(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void save(String email, String token) {
        Certification certification = Certification.builder()
                .email(email)
                .token(token)
                .certification(false)
                .exp(300000L)
                .build();
        certificationRepository.save(certification);
    }

    public Optional<Certification> findCertificationByToken(String token) {
        return certificationRepository.findByToken(token);
    }

    public NormalUser save(NormalUser normalUser) {
        return normalUserRepository.save(normalUser);
    }

    public void save(Field field) {
        fieldRepository.save(field);
    }
}
