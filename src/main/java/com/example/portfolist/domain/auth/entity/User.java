package com.example.portfolist.domain.auth.entity;

import com.example.portfolist.domain.mypage.entity.Notification;
import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.comment.ReComment;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pk;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normalUser_pk")
    private NormalUser normalUser;

    @Column(name = "github_id")
    private String githubId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "url")
    private String url;

    @Column(name = "notification_status")
    private boolean notificationStatus;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Portfolio> portfolioList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Touching> touchingList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReComment>  reCommentList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Field> fieldList;

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Notification> notificationList;

    public void updateUserInfo(String name, String introduce) {
        if (!StringUtil.isNullOrEmpty(name)) {
            this.name = name;
        }
        if (!StringUtil.isNullOrEmpty(introduce)) {
            this.introduce = introduce;
        }
    }

    public void updateProfile(String url) {
        this.url = url;
    }

    public void updateChange(String name, String url) {
        this.name = name;
        this.url = url;
    }

}
