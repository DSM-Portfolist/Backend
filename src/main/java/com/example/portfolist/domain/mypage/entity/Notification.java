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
    @JoinColumn(name = "toUser_pk")
    private User toUser;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "type")
    private NoticeType type;

    @ManyToOne
    @JoinColumn(name = "fromUser_pk")
    private User fromUser;

}
