package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.portfolio.dto.request.ContentRequest;
import com.example.portfolist.domain.portfolio.dto.response.CommentListResponse;
import com.example.portfolist.domain.portfolio.dto.response.ReCommentResponse;

import java.util.List;

public interface CommentService {
    void createComment(long portfolioId, ContentRequest request);

    void deleteComment(long commentId);

    void createReComment(long commentId, ContentRequest request);

    void deleteReComment(long reCommentId);

    CommentListResponse getCommentList(long portfolioId);

    List<ReCommentResponse> getReCommentList(long commentId);

}
