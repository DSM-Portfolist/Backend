package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.dto.request.CommentRequest;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.comment.ReComment;
import com.example.portfolist.domain.portfolio.exception.CommentNotFoundException;
import com.example.portfolist.domain.portfolio.exception.PortfolioNotFoundException;
import com.example.portfolist.domain.portfolio.repository.comment.CommentRepository;
import com.example.portfolist.domain.portfolio.repository.comment.ReCommentRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioRepository;
import com.example.portfolist.global.error.exception.PermissionDeniedException;
import com.example.portfolist.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PortfolioRepository portfolioRepository;
    private final ReCommentRepository reCommentRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public void createComment(long portfolioId, CommentRequest request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(PortfolioNotFoundException::new);

        commentRepository.save(Comment.builder()
                .portfolio(portfolio)
                .user(getCurrentUser())
                .date(LocalDate.now())
                .deleteYN('N')
                .content(request.getContent())
                .build());
    }

    @Override
    @Transactional
    public void deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if (comment.getUser().getPk() == getCurrentUser().getPk())
            comment.deleteComment();
        else
            throw new PermissionDeniedException();
    }

    @Override
    public void createReComment(long commentId, CommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        reCommentRepository.save(ReComment.builder()
                .user(getCurrentUser())
                .comment(comment)
                .date(LocalDate.now())
                .content(request.getContent())
                .build());
    }

    @Override
    public void deleteReComment(long reCommentId) {
        ReComment reComment = reCommentRepository.findById(reCommentId)
                .orElseThrow(CommentNotFoundException::new);

        if (reComment.getUser().getPk() == getCurrentUser().getPk())
            reCommentRepository.deleteById(reCommentId);
        else
            throw new PermissionDeniedException();
    }

    private User getCurrentUser() {
        return authenticationFacade.getUser();
    }

}
