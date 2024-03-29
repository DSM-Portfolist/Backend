package com.example.portfolist.domain.auth.repository;

import com.example.portfolist.domain.auth.entity.Field;
import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.entity.redis.Certification;
import com.example.portfolist.domain.auth.entity.redis.RefreshToken;
import com.example.portfolist.domain.auth.exception.UserNotFoundException;
import com.example.portfolist.domain.auth.repository.repository.FieldKindRepository;
import com.example.portfolist.domain.auth.repository.repository.FieldRepository;
import com.example.portfolist.domain.auth.repository.repository.NormalUserRepository;
import com.example.portfolist.domain.auth.repository.repository.UserRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.CertificationRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final CertificationRepository certificationRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;
    private final NormalUserRepository normalUserRepository;
    private final FieldRepository fieldRepository;
    private final FieldKindRepository fieldKindRepository;

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
                .exp(300L)
                .build();
        certificationRepository.save(certification);
    }

    public Optional<Certification> findCertificationByToken(String token) {
        return certificationRepository.findByToken(token);
    }

    public NormalUser save(NormalUser normalUser) {
        return normalUserRepository.save(normalUser);
    }

    public void delete(List<Field> fields) {
        fieldRepository.deleteAll(fields);
    }

    public void save(List<Field> fields) {
        fieldRepository.saveAll(fields);
    }

    public List<Field> findFieldByUser(User user) {
        return fieldRepository.findByUser(user);
    }

    public void deleteFieldByUser(User user) {
        fieldRepository.deleteByUser(user);
    }

    public void deleteNormalByUser(User user) {
        normalUserRepository.delete(user.getNormalUser());
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public List<FieldKind> findFieldKindAll() {
        return fieldKindRepository.findAll();
    }

    public void changeCertification(Certification certification) {
        certificationRepository.delete(certification);
        certification = Certification.builder()
                .email(certification.getEmail())
                .token(certification.getToken())
                .certification(true)
                .exp(300L)
                .build();
        certificationRepository.save(certification);
    }

    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

}
