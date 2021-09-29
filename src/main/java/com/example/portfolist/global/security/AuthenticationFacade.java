package com.example.portfolist.global.security;

import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.global.error.exception.NotNormalUserException;
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

    public NormalUser getNormalUser() {
        Authentication authentication = getAuthentication();
        User user = (User)authentication.getPrincipal();
        NormalUser normalUser = user.getNormalUser();
        if(normalUser == null) {
            throw new NotNormalUserException();
        }
        return normalUser;
    }

}
