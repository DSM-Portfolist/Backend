package com.example.portfolist.domain.portfolio.service;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.mypage.entity.NoticeType;
import com.example.portfolist.domain.portfolio.dto.request.ContentRequest;
import com.example.portfolist.domain.portfolio.dto.response.CommentListResponse;
import com.example.portfolist.domain.portfolio.dto.response.CommentResponse;
import com.example.portfolist.domain.portfolio.dto.response.ReCommentResponse;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.exception.CommentNotFoundException;
import com.example.portfolist.domain.portfolio.exception.PortfolioNotFoundException;
import com.example.portfolist.domain.portfolio.repository.comment.CommentRepository;
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

    private final AuthenticationFacade authenticationFacade;

    private final GlobalEventPublisher eventPublisher;

    public CommentListResponse queryCommentList(long portfolioId) {

        List<CommentResponse> comments = commentRepository.findByPortfolioPk(portfolioId).stream()
                .map(comment -> CommentResponse.of(comment, comment.getUser() != null && getCurrentUser().getPk() == comment.getUser().getPk()))
                .collect(Collectors.toList());

        return new CommentListResponse(comments);
    }

    public List<ReCommentResponse> queryReCommentList(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        return commentRepository.findAllBypComment(comment).stream()
                .map(reComment -> ReCommentResponse.of(reComment, getCurrentUser().getPk() == reComment.getUser().getPk()))
                .collect(Collectors.toList());
    }


    public void createComment(long portfolioId, ContentRequest request) {
        Portfolio portfolio = getPortfolio(portfolioId);
        User user = getCurrentUser();

        Comment pComment = request.getCommentId() == null ?
                null : commentRepository.findById(request.getCommentId()).orElseThrow(CommentNotFoundException::new);

        commentRepository.save(Comment.builder()
                .portfolio(pComment == null ? portfolio : null)
                .user(user)
                .date(LocalDate.now())
                .deleteYN('N')
                .content(request.getContent())
                .pComment(pComment)
                .build());

        if (pComment == null)
            eventPublisher.makeNotice(user, portfolio.getUser(), NoticeType.COMMENT);
        else
            eventPublisher.makeNotice(user, portfolio.getUser(), NoticeType.RECOMMENT);
    }

    @Transactional
    public void deleteComment(long commentId) {
        Comment comment = getComment(commentId);

        if (comment.getUser().getPk() == getCurrentUser().getPk()) {
            if (comment.getCommentList().size() == 0)
                commentRepository.deleteById(commentId);
            else
                comment.disableComment();
        }
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
