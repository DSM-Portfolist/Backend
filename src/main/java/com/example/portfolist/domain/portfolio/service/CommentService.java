package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.mypage.entity.NoticeType;
import com.example.portfolist.domain.portfolio.dto.request.ContentRequest;
import com.example.portfolist.domain.portfolio.dto.response.CommentListResponse;
import com.example.portfolist.domain.portfolio.dto.response.CommentResponse;
import com.example.portfolist.domain.portfolio.dto.response.ReCommentResponse;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.comment.ReComment;
import com.example.portfolist.domain.portfolio.exception.CommentNotFoundException;
import com.example.portfolist.domain.portfolio.exception.PortfolioNotFoundException;
import com.example.portfolist.domain.portfolio.repository.comment.CommentRepository;
import com.example.portfolist.domain.portfolio.repository.comment.ReCommentRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioRepository;
import com.example.portfolist.global.error.exception.PermissionDeniedException;
import com.example.portfolist.global.event.GlobalEventPublisher;
import com.example.portfolist.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PortfolioRepository portfolioRepository;
    private final ReCommentRepository reCommentRepository;

    private final AuthenticationFacade authenticationFacade;

    private final GlobalEventPublisher eventPublisher;

    public CommentListResponse getCommentList(long portfolioId) {

        List<CommentResponse> comments = commentRepository.findByPortfolioPk(portfolioId).stream()
                .map(comment -> CommentResponse.of(comment, comment.getUser() != null && getCurrentUser().getPk() == comment.getUser().getPk()))
                .collect(Collectors.toList());

        return new CommentListResponse(comments);
    }

    public void createComment(long portfolioId, ContentRequest request) {
        Portfolio portfolio = getPortfolio(portfolioId);
        User user = getCurrentUser();
        commentRepository.save(Comment.builder()
                .portfolio(portfolio)
                .user(user)
                .date(LocalDate.now())
                .deleteYN('N')
                .content(request.getContent())
                .build());
        eventPublisher.makeNotice(user, portfolio.getUser(), NoticeType.COMMENT);
    }

    @Transactional
    public void deleteComment(long commentId) {
        Comment comment = getComment(commentId);

        if (comment.getUser().getPk() == getCurrentUser().getPk()) {
            if (comment.getReCommentList().size() == 0)
                commentRepository.deleteById(commentId);
            else
                comment.deleteComment();
        }
        else
            throw new PermissionDeniedException();
    }

    public List<ReCommentResponse> getReCommentList(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        return reCommentRepository.findAllByComment(comment).stream()
                .map(reComment -> ReCommentResponse.of(reComment, getCurrentUser().getPk() == reComment.getUser().getPk()))
                .collect(Collectors.toList());
    }

    public void createReComment(long commentId, ContentRequest request) {
        Comment comment = getComment(commentId);
        User user = getCurrentUser();
        reCommentRepository.save(ReComment.builder()
                .user(getCurrentUser())
                .comment(getComment(commentId))
                .date(LocalDate.now())
                .content(request.getContent())
                .build());
        eventPublisher.makeNotice(user, comment.getUser(), NoticeType.RECOMMENT);
    }

    public void deleteReComment(long reCommentId) {
        ReComment reComment = reCommentRepository.findById(reCommentId)
                .orElseThrow(CommentNotFoundException::new);

        if (reComment.getUser().getPk() == getCurrentUser().getPk())
            reCommentRepository.deleteById(reCommentId);
        else
            throw new PermissionDeniedException();
    }

    public void reportComment(long commentId, ContentRequest request) {

    }

    private User getCurrentUser() {
        return authenticationFacade.getUser();
    }

    private Portfolio getPortfolio(long portfolioId) {
        return portfolioRepository.findById(portfolioId).orElseThrow(PortfolioNotFoundException::new);
    }

    private Comment getComment(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}
