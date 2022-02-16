package com.example.portfolist.domain.portfolio.entity;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.container.Container;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(length = 45, nullable = false)
    private String introduce;

    private String link;

    private String url;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "tinyint(1) default true")
    private boolean isOpen;

    @Column(length = 1)
    private String mainIcon;

    private String thumbnail;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.REMOVE)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.REMOVE)
    private final List<MoreInfo> moreInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.REMOVE)
    private final List<Container> containerList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.REMOVE)
    private final List<CertificateContainer> certificateContainerList = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.REMOVE)
    private final List<Touching> touchingList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.REMOVE)
    private final List<PortfolioField> portfolioFields = new ArrayList<>();

    public void update(PortfolioRequest request) {
        this.title = request.getTitle();
        this.introduce = request.getIntroduce();
        this.link = request.getLink();
        this.url = request.getFile();
        this.mainIcon = request.getIcon();
        this.isOpen = request.isOpen();
        this.thumbnail = request.getThumbnail();
    }

    public static Portfolio toEntity(PortfolioRequest request, User user) {
        return Portfolio.builder()
                .user(user)
                .title(request.getTitle())
                .introduce(request.getIntroduce())
                .link(request.getLink())
                .url(request.getFile())
                .date(LocalDateTime.now())
                .isOpen(request.isOpen())
                .mainIcon(request.getIcon())
                .thumbnail(request.getThumbnail())
                .build();
    }

    public void updateThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
