package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.domain.portfolio.dto.request.CommentRequest;
import com.example.portfolist.domain.portfolio.dto.response.CommentListResponse;
import com.example.portfolist.domain.portfolio.dto.response.ReCommentResponse;
import com.example.portfolist.domain.portfolio.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment/{portfolioId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentListResponse getCommentList(@PathVariable long portfolioId) {
        return commentService.getCommentList(portfolioId);
    }

    @PostMapping("/comment/{portfolioId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@PathVariable long portfolioId, @RequestBody CommentRequest request) {
        commentService.createComment(portfolioId, request);
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
    }

    @GetMapping("/re-comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReCommentResponse> getReCommentList(@PathVariable long commentId) {
        return commentService.getReCommentList(commentId);
    }

    @PostMapping("/re-comment/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReComment(@PathVariable long commentId, @RequestBody CommentRequest request) {
        commentService.createReComment(commentId, request);
    }

    @DeleteMapping("/re-comment/{reCommentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReComment(@PathVariable long reCommentId) {
        commentService.deleteReComment(reCommentId);
    }

}
