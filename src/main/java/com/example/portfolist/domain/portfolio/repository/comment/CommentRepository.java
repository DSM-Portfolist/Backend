package com.example.portfolist.domain.portfolio.repository.comment;

import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
