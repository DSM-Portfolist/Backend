package com.example.portfolist.domain.auth.repository.repository;

import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalUserRepository extends JpaRepository<NormalUser, Long> {

    void deleteByUser(User user);

}
