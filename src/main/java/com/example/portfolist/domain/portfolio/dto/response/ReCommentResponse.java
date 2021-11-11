package com.example.portfolist.domain.portfolio.dto.response;

import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.comment.ReComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReCommentResponse {

    private UserDto user;

    private boolean isMine;

    private long reCommentId;

    private String reCommentContent;

    private LocalDate rcDate;

    public static ReCommentResponse of(ReComment reComment, Boolean isMine) {
        return ReCommentResponse.builder()
                .user(UserDto.of(reComment.getUser()))
                .isMine(isMine)
                .reCommentId(reComment.getPk())
                .reCommentContent(reComment.getContent())
                .rcDate(reComment.getDate())
                .build();
    }
}
