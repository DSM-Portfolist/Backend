package com.example.portfolist.domain.mypage.entity;

import com.example.portfolist.domain.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "toUser_pk", nullable = false)
    private User toUser;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @ManyToOne
    @JoinColumn(name = "fromUser_pk", nullable = false)
    private User fromUser;

}
