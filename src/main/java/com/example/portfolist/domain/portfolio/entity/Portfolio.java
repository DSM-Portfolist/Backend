package com.example.portfolist.domain.portfolio.entity;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.container.Container;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(length = 45, nullable = false)
    private String introduce;

    @Column(length = 100)
    private String link;

    @Column(length = 100)
    private String url;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "tinyint(1) default true")
    private boolean isOpen;

    @Column(length = 1)
    private Character mainIcon;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "portfolio")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "portfolio")
    private List<MoreInfo> moreInfoList;

    @OneToMany(mappedBy = "portfolio")
    private List<Container> containerList;

    @OneToMany(mappedBy = "portfolio")
    private List<Certificate> certificateList;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "portfolio")
    private List<Touching> touchingList;

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioField> portfolioFields;
}
