package com.example.portfolist.domain.auth.repository;

import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.entity.redis.Certification;
import com.example.portfolist.domain.auth.exception.EmailNotAuthorizedException;
import com.example.portfolist.domain.auth.exception.FieldNotFoundException;
import com.example.portfolist.domain.auth.exception.UserDuplicatedException;
import com.example.portfolist.domain.auth.exception.UserNotFoundException;
import com.example.portfolist.domain.auth.repository.repository.FieldKindRepository;
import com.example.portfolist.domain.auth.repository.repository.UserRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final UserRepository userRepository;
    private final FieldKindRepository fieldKindRepository;
    private final CertificationRepository certificationRepository;

    public User findByNormalUserEmail(String email) {
        return userRepository.findByNormalUserEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public void checkConflictEmail(String email) {
        if(userRepository.existsByNormalUserEmail(email)) {
            throw new UserDuplicatedException();
        }
    }

    public void checkAuthorizedEmail(String email) {
        Optional<Certification> certification = certificationRepository.findByEmail(email);
        if(certification.isEmpty() || !certification.get().isCertification()) {
            throw new EmailNotAuthorizedException();
        }
    }

    public FieldKind findByFieldKindId(int pk) {
        return fieldKindRepository.findById(pk)
                .orElseThrow(FieldNotFoundException::new);
    }

}