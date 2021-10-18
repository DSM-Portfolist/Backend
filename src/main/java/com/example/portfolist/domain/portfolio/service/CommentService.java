package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.portfolio.dto.request.CommentRequest;

public interface CommentService {
    void createComment(long portfolioId, CommentRequest request);
    void deleteComment(long commentId);
    void createReComment(long commentId, CommentRequest request);
    void deleteReComment(long reCommentId);
}
