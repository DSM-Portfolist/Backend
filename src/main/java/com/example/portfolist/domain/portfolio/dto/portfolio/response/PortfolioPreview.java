package com.example.portfolist.domain.portfolio.dto.portfolio.response;

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

    private String date;

    private long totalTouching;

    private long totalComment;

    private boolean touched;

    private long userId;

    private String name;

    private String profileImg;

    @QueryProjection
    public PortfolioPreview(long id, String pofolImg, String title, String introduce, String date, long userId, String name, String profileImg, boolean touched) {
        this.id = id;
        this.pofolImg = pofolImg;
        this.title = title;
        this.introduce = introduce;
        this.date = date;
        this.userId = userId;
        this.name = name;
        this.profileImg = profileImg;
        this.touched = touched;
    }
}
