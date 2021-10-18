package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.domain.portfolio.dto.request.CommentRequest;
import com.example.portfolist.domain.portfolio.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

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
