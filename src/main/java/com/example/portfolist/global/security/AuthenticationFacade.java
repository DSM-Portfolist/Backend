package com.example.portfolist.global.security;

import com.example.portfolist.domain.auth.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User getUser() {
        Authentication authentication = getAuthentication();
        return (User)authentication.getPrincipal();
    }

}
