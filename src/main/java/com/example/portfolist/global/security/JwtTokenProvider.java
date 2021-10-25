package com.example.portfolist.global.security;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.repository.repository.UserRepository;
import com.example.portfolist.global.error.exception.InvalidTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserRepository userRepository;

    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Value("${auth.jwt.access}")
    private Long accessLifespan;

    @Value("${auth.jwt.refresh}")
    private Long refreshLifespan;

    public String generateAccessToken(Long id) { return makingToken(String.valueOf(id), "access", accessLifespan); }

    public String generateRefreshToken(Long id) { return makingToken(String.valueOf(id), "refresh", refreshLifespan*3); }

    public boolean isAccessToken(String token){
        return checkTokenType(token, "access");
    }

    public boolean isRefreshToken(String token){
        return checkTokenType(token, "refresh");
    }

    public Long getId(String token) {
        try {
            String id = Jwts.parser().setSigningKey(encodingSecretKey()).parseClaimsJws(token).getBody().getSubject();
            return Long.parseLong(id);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (checkToken(token)) {
            return token.substring(7);
        }
        return null;
    }

    private Boolean checkToken(String token) {
        return token != null && token.startsWith("Bearer ");
    }

    public Authentication getAuthentication(String token) {
        Long pk = getId(token);
        Optional<User> user = userRepository.findById(pk);
        if (user.isPresent()) {
            return new UsernamePasswordAuthenticationToken(user.get(), "", getAuthorities());
        }
        return new UsernamePasswordAuthenticationToken(null, "", null);
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }


    private boolean checkTokenType(String token, String typeKind) {
        try {
            String type = Jwts.parser().setSigningKey(encodingSecretKey()).parseClaimsJws(token).getBody().get("type", String.class);
            return type.equals(typeKind);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    private String makingToken(String id, String type, Long time){
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + (time)*1000))
                .signWith(SignatureAlgorithm.HS512, encodingSecretKey())
                .setIssuedAt(new Date())
                .setSubject(id)
                .claim("type", type)
                .compact();
    }

    private String encodingSecretKey(){
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

}
