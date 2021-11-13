package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PortfolioPreview {

    private long id;

    private String thumbnail;

    private List<String> field;

    private String title;

    private String introduce;

    private LocalDate date;

    private int totalTouching;

    private int totalComment;

    private boolean touched;

    private UserDto user;

    @QueryProjection
    public PortfolioPreview(long id, String thumbnail, String title, String introduce, LocalDate date, long userId, String name, String profileImg, boolean touched, int totalComment, int totalTouching) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.introduce = introduce;
        this.date = date;
        this.user = UserDto.builder()
                .userId(userId)
                .name(name)
                .profileImg(profileImg)
                .build();
        this.touched = touched;
        this.totalComment = totalComment;
        this.totalTouching = totalTouching;
    }

    public static PortfolioPreview of(Portfolio portfolio) {
        return PortfolioPreview.builder()
                .id(portfolio.getPk())
                .thumbnail(portfolio.getUrl())
                .title(portfolio.getTitle())
                .introduce(portfolio.getIntroduce())
                .date(portfolio.getDate())
                .user(UserDto.of(portfolio.getUser()))
                .build();
    }
}
