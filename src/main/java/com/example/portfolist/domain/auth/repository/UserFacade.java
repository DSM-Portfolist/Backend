package com.example.portfolist.domain.auth.repository;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.exception.UserNotFoundException;
import com.example.portfolist.domain.auth.repository.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserJpaRepository userJpaRepository;

    public User findByNormalUserEmail(String email) {
        return userJpaRepository.findByNormalUserEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}