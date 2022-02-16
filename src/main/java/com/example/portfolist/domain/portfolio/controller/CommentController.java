package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.domain.portfolio.dto.request.ContentRequest;
import com.example.portfolist.domain.portfolio.dto.response.CommentListResponse;
import com.example.portfolist.domain.portfolio.dto.response.ReCommentResponse;
import com.example.portfolist.domain.portfolio.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment/{portfolioId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentListResponse queryCommentList(@PathVariable long portfolioId) {
        return commentService.queryCommentList(portfolioId);
    }

    @GetMapping("/re-comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReCommentResponse> queryReCommentList(@PathVariable long commentId) {
        return commentService.queryReCommentList(commentId);
    }

    @PostMapping("/comment/{portfolioId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@PathVariable long portfolioId, @Valid @RequestBody ContentRequest request) {
        commentService.createComment(portfolioId, request);
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
    }

    @PostMapping("/comment/{commentId}/report")
    @ResponseStatus(HttpStatus.CREATED)
    public void reportComment(@PathVariable long commentId, @Valid @RequestBody ContentRequest request) {
        commentService.reportComment(commentId, request);
    }

}
