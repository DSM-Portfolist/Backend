package com.example.portfolist.domain.auth.entity;

import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.comment.ReComment;
import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
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

    @OneToMany(mappedBy = "user")
    private List<Portfolio> portfolioList;

    @OneToMany(mappedBy = "user")
    private List<Touching> touchingList;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user")
    private List<ReComment>  recommentList;

    @OneToMany(mappedBy = "user")
    private List<Field> fieldList;
}
