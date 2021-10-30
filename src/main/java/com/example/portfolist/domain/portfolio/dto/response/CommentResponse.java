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

    private Long userId;

    private String name;

    private String profileImg;

    private Boolean isMine;

    private long commentId;

    private String commentContent;

    private LocalDate cDate;

    private List<ReCommentResponse> reCommentList;

    public static CommentResponse of(Comment comment, Boolean isMine, List<ReCommentResponse> reCommentList) {
        if (comment.getDeleteYN().equals('Y')) {
            return CommentResponse.builder()
                    .userId(null)
                    .name(null)
                    .profileImg(null)
                    .isMine(null)
                    .commentId(comment.getPk())
                    .commentContent(null)
                    .cDate(comment.getDate())
                    .reCommentList(reCommentList)
                    .build();
        }
        return CommentResponse.builder()
                .userId(comment.getUser().getPk())
                .name(comment.getUser().getName())
                .profileImg(comment.getUser().getUrl())
                .isMine(isMine)
                .commentId(comment.getPk())
                .commentContent(comment.getContent())
                .cDate(comment.getDate())
                .reCommentList(reCommentList)
                .build();
    }
}
