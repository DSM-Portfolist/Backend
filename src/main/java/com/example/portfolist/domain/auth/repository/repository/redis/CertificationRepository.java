package com.example.portfolist.domain.auth.repository.repository.redis;

import com.example.portfolist.domain.auth.entity.redis.Certification;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CertificationRepository extends CrudRepository<Certification, Long> {
    Optional<Certification> findByToken(String token);
    Optional<Certification> findByEmail(String email);
}
