package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.portfolio.dto.request.CommentRequest;
import com.example.portfolist.domain.portfolio.dto.response.CommentListResponse;
import com.example.portfolist.domain.portfolio.dto.response.ReCommentResponse;

import java.util.List;

public interface CommentService {
    void createComment(long portfolioId, CommentRequest request);

    void deleteComment(long commentId);

    void createReComment(long commentId, CommentRequest request);

    void deleteReComment(long reCommentId);

    CommentListResponse getCommentList(long portfolioId);

    List<ReCommentResponse> getReCommentList(long commentId);

}
