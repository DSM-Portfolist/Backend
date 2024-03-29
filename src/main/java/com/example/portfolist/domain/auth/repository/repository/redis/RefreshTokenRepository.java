package com.example.portfolist.domain.auth.repository.repository.redis;

import com.example.portfolist.domain.auth.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String token);
}
