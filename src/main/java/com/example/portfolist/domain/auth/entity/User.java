package com.example.portfolist.domain.auth.entity;

import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.comment.ReComment;
import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Portfolio> portfolioList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Touching> touchingList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReComment>  recommentList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Field> fieldList;

    public void updateUserInfo(String name, String introduce) {
        if (!StringUtil.isNullOrEmpty(name)) {
            this.name = name;
        }
        if (!StringUtil.isNullOrEmpty(introduce)) {
            this.introduce = introduce;
        }
    }

}
