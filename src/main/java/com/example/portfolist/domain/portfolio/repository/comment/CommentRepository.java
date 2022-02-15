package com.example.portfolist.domain.portfolio.repository.comment;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    void deleteByUser(User user);

    List<Comment> findByPortfolioPk(long portfolioId);

    List<Comment> findAllBypComment(Comment pComment);
}
