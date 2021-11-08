package com.example.portfolist.domain.portfolio.repository.comment;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.comment.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReCommentRepository extends JpaRepository<ReComment,Long> {

    void deleteByCommentUser(User user);

    List<ReComment> findAllByComment(Comment comment);
}
