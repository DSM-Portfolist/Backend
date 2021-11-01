package com.example.portfolist.domain.portfolio.entity.comment;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private LocalDate date;

    @Column(length = 300)
    private String content;

    @Column(nullable = false, columnDefinition = "char(1) default 'N'")
    private Character deleteYN;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private final List<ReComment> reCommentList = new ArrayList<>();

    public void deleteComment() {
        this.deleteYN = 'Y';
        this.content = null;
        this.user = null;
    }
}
