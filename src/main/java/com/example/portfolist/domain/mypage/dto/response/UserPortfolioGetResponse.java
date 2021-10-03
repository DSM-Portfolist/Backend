package com.example.portfolist.domain.mypage.dto.response;

import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
public class UserPortfolioGetResponse {

    private long id;
    private String url;
    private List<String> field;
    private Character icon;
    private String title;
    private String introduce;
    private int comment;
    private int touching;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    public static UserPortfolioGetResponse from(Portfolio portfolio) {
        List<String> fields = portfolio.getPortfolioFields().stream()
                .map(field -> field.getFieldKind().getContent())
                .collect(Collectors.toList());

        return UserPortfolioGetResponse.builder()
                .id(portfolio.getPk())
                .url(portfolio.getUrl())
                .field(fields)
                .icon(portfolio.getMainIcon())
                .title(portfolio.getTitle())
                .introduce(portfolio.getIntroduce())
                .comment(portfolio.getCommentList().size())
                .touching(portfolio.getTouchingList().size())
                .date(portfolio.getDate())
                .build();
    }

}
