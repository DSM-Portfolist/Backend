package com.example.portfolist.global.security;

import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.repository.repository.NormalUserRepository;
import com.example.portfolist.domain.auth.repository.repository.UserRepository;
import com.example.portfolist.global.error.exception.NotNormalUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {

    private final UserRepository userRepository;
    private final NormalUserRepository normalUserRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User getUser() {
        Authentication authentication = getAuthentication();
        User user = (User)authentication.getPrincipal();
        return userRepository.save(user);
    }

    public NormalUser getNormalUser() {
        Authentication authentication = getAuthentication();
        User user = (User)authentication.getPrincipal();
        NormalUser normalUser = userRepository.save(user).getNormalUser();
        if(normalUser == null) {
            throw new NotNormalUserException();
        }
        return normalUserRepository.save(normalUser);
    }

}
