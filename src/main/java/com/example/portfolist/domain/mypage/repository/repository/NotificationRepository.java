package com.example.portfolist.domain.mypage.repository.repository;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.mypage.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    void deleteByToUserAndIsReadIsTrue(User user);
    List<Notification> findByToUser(User user);

}
