package com.example.portfolist.domain.auth.repository.repository.redis;

import com.example.portfolist.domain.auth.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    default void saveRefreshToken(String token, Long refreshLifespan) {
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .exp(refreshLifespan)
                .build();
        this.save(refreshToken);
    }
}
