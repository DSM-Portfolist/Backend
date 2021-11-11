package com.example.portfolist.domain.mypage.dto.response;

import com.amazonaws.util.CollectionUtils;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TouchingPortfolioGetRes {

    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PROTECTED)
    public static class ContentUser {

        private long userId;
        private String name;
        private String profileImg;

        public static ContentUser from(User user) {
            return ContentUser.builder()
                    .userId(user.getPk())
                    .name(user.getName())
                    .profileImg(user.getUrl())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PROTECTED)
    public static class Content {

        private long id;
        private String url;
        private List<String> field;
        private String icon;
        private String title;
        private String introduce;
        private ContentUser user;
        private int totalComment;
        private int totalTouching;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate date;

        public static Content from(Touching touching) {
            Portfolio portfolio = touching.getPortfolio();
            List<String> fields = null;
            if(!CollectionUtils.isNullOrEmpty(portfolio.getPortfolioFields())) {
                fields = portfolio.getPortfolioFields().stream()
                        .map(field -> field.getFieldKind().getContent())
                        .collect(Collectors.toList());
            }

            return Content.builder()
                    .id(portfolio.getPk())
                    .url(portfolio.getUrl())
                    .field(fields)
                    .icon(portfolio.getMainIcon())
                    .title(portfolio.getTitle())
                    .introduce(portfolio.getIntroduce())
                    .user(ContentUser.from(portfolio.getUser()))
                    .totalComment(CollectionUtils.isNullOrEmpty(portfolio.getCommentList()) ?
                            0 : portfolio.getCommentList().size())
                    .totalTouching(CollectionUtils.isNullOrEmpty(portfolio.getTouchingList()) ?
                            0 : portfolio.getTouchingList().size())
                    .date(portfolio.getDate())
                    .build();
        }

    }


    @Getter
    @AllArgsConstructor
    @Builder(access = AccessLevel.PROTECTED)
    public static class Response {

        private long totalElements;
        private List<Content> content;

        public static Response from(Page<Touching> touchings) {
            List<Content> contents = touchings.stream()
                    .map(Content::from).collect(Collectors.toList());
            return Response.builder()
                    .totalElements(touchings.getTotalElements())
                    .content(contents)
                    .build();
        }
    }

}
