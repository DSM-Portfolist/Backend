package com.example.portfolist.domain.portfolio.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class PortfolioPreview {

    private long id;

    private String pofolImg;

    private List<String> field;

    private String title;

    private String introduce;

    private LocalDate date;

    private int totalTouching;

    private int totalComment;

    private Boolean touched;

    private long userId;

    private String name;

    private String profileImg;

    @QueryProjection
    public PortfolioPreview(long id, String pofolImg, String title, String introduce, LocalDate date, long userId, String name, String profileImg, boolean touched, int totalComment, int totalTouching) {
        this.id = id;
        this.pofolImg = pofolImg;
        this.title = title;
        this.introduce = introduce;
        this.date = date;
        this.userId = userId;
        this.name = name;
        this.profileImg = profileImg;
        this.touched = touched;
        this.totalComment = totalComment;
        this.totalTouching = totalTouching;
    }
}
