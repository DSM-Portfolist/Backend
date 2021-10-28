package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private long userId;

    private String name;

    private String profileImg;

    private Boolean isMine;

    private long commentId;

    private String commentContent;

    private LocalDate cDate;

    private List<ReCommentResponse> reCommentList;

    public static CommentResponse toDto(Comment comment) {
        return CommentResponse.builder()
                .userId(comment.getUser().getPk())
                .name(comment.getUser().getName())
                .profileImg(comment.getUser().getUrl())
                .isMine(false)
                .commentId(comment.getPk())
                .commentContent(comment.getContent())
                .cDate(comment.getDate())
                .reCommentList(comment.getReCommentList().stream()
                        .map(ReCommentResponse::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
