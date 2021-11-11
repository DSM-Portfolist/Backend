package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private UserDto user;

    private boolean isMine;

    private long commentId;

    private String commentContent;

    private LocalDate cDate;

    private boolean reCommentExist;

    public static CommentResponse of(Comment comment, Boolean isMine) {
        if (comment.getDeleteYN().equals('Y')) {
            return CommentResponse.builder()
                    .user(null)
                    .isMine(false)
                    .commentId(comment.getPk())
                    .commentContent(null)
                    .cDate(comment.getDate())
                    .reCommentExist(comment.getReCommentList().size() > 0)
                    .build();
        }
        return CommentResponse.builder()
                .user(UserDto.of(comment.getUser()))
                .isMine(isMine)
                .commentId(comment.getPk())
                .commentContent(comment.getContent())
                .cDate(comment.getDate())
                .reCommentExist(comment.getReCommentList().size() > 0)
                .build();
    }
}
