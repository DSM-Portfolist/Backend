package com.example.portfolist.domain.mypage.dto.response;

import com.amazonaws.util.CollectionUtils;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
public class UserPortfolioGetResponse {

    private long id;
    private List<String> field;
    private String icon;
    private String title;
    private String introduce;
    private String thumbnail;
    private int totalComment;
    private int totalTouching;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    private boolean isOpen;

    public static UserPortfolioGetResponse from(Portfolio portfolio) {
        List<String> fields = null;
        if (!CollectionUtils.isNullOrEmpty(portfolio.getPortfolioFields())) {
            fields = portfolio.getPortfolioFields().stream()
                    .map(field -> field.getFieldKind().getContent())
                    .collect(Collectors.toList());
        }

        return UserPortfolioGetResponse.builder()
                .id(portfolio.getId())
                .field(fields)
                .icon(portfolio.getMainIcon())
                .thumbnail(portfolio.getThumbnail())
                .title(portfolio.getTitle())
                .introduce(portfolio.getIntroduce())
                .totalComment(CollectionUtils.isNullOrEmpty(portfolio.getCommentList()) ?
                        0 : portfolio.getCommentList().size())
                .totalTouching(CollectionUtils.isNullOrEmpty(portfolio.getTouchingList()) ?
                        0 : portfolio.getTouchingList().size())
                .date(portfolio.getDate())
                .isOpen(portfolio.isOpen())
                .build();
    }

}
