package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.dto.request.ContentRequest;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.comment.ReComment;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends ApiTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    User user;
    Portfolio portfolio;

    @BeforeEach
    public void before() {

        NormalUser normalUser = createNormalUser("testtest@gmail.com", "testPassword");

        user = createUser("고도현현", "고도현입니다", normalUser);

        portfolio = createPortfolio(user, "포트폴리오 제목", "포트폴리오 소개");
    }

    @Test
    public void 댓글달기_201() throws Exception {

        Portfolio portfolio = createPortfolio(user, "testTitle", "testIntroduce");

        setToken(jwtTokenProvider.generateAccessToken(user.getPk()));

        requestMvc(post("/comment/" + portfolio.getPk())
                , new ContentRequest("와 포트폴리오가 너무 멋져요"))
                .andExpect(status().isCreated());

        assertEquals(commentRepository.findAll().size(), 1);
        assertEquals(commentRepository.findAll().get(0).getContent(), "와 포트폴리오가 너무 멋져요");
    }

    @Test
    public void 댓글달기_권한없음_401() throws Exception {
        Portfolio portfolio = createPortfolio(user, "testTitle", "testIntroduce");

        requestMvc(post("/comment/" + portfolio.getPk())
                , new ContentRequest("와 포트폴리오가 너무 멋져요"))
                .andExpect(status().isUnauthorized());

        assertEquals(commentRepository.findAll().size(), 0);
    }

    @Test
    public void 댓글_삭제하기_204() throws Exception {
        Comment comment = createComment(user, portfolio, "와");

        setToken(jwtTokenProvider.generateAccessToken(user.getPk()));

        requestMvc(delete("/comment/" + comment.getPk()))
            .andExpect(status().isNoContent());
    }

    @Test
    public void 댓글_삭제_권한없음_403() throws Exception {

        Comment comment = createComment(user, portfolio, "와");

        User noPermissionUser = createUser("홍길동", "동에번쩍", null);

        setToken(jwtTokenProvider.generateAccessToken(noPermissionUser.getPk()));

        requestMvc(delete("/comment/" + comment.getPk()))
                .andExpect(status().isForbidden());

        assertEquals(commentRepository.findAll().size(), 1);
    }

    @Test
    public void 대댓글달기_201() throws Exception {
        Comment comment = createComment(user, portfolio, "test Content");

        setToken(jwtTokenProvider.generateAccessToken(user.getPk()));

        requestMvc(post("/re-comment/" + comment.getPk())
                , new ContentRequest("와 포트폴리오가 너무 멋져요"))
                .andExpect(status().isCreated());

        assertEquals(reCommentRepository.findAll().size(), 1);
        assertEquals(reCommentRepository.findAll().get(0).getContent(), "와 포트폴리오가 너무 멋져요");
    }

    @Test
    public void 대댓글달기_권한없음_401() throws Exception {
        Portfolio portfolio = createPortfolio(user, "testTitle", "testIntroduce");

        Comment comment = createComment(user, portfolio, "test Content");

        requestMvc(post("/re-comment/" + comment.getPk())
                , new ContentRequest("와 포트폴리오가 너무 멋져요"))
                .andExpect(status().isUnauthorized());

        assertEquals(reCommentRepository.findAll().size(), 0);
    }

    @Test
    public void 대댓글_삭제하기_204() throws Exception {
        Comment comment = createComment(user, portfolio, "test Content");
        ReComment reComment = createReComment(user, comment, "와");

        setToken(jwtTokenProvider.generateAccessToken(user.getPk()));

        requestMvc(delete("/re-comment/" + reComment.getPk()))
                .andExpect(status().isNoContent());

        assertEquals(reCommentRepository.findAll().size(), 0);
    }

    @Test
    public void 대댓글_삭제_권한없음_403() throws Exception {
        Comment comment = createComment(user, portfolio, "와");
        ReComment reComment = createReComment(user, comment, "와");

        User noPermissionUser = createUser("홍길동", "동에번쩍", null);

        setToken(jwtTokenProvider.generateAccessToken(noPermissionUser.getPk()));

        requestMvc(delete("/re-comment/" + reComment.getPk()))
                .andExpect(status().isForbidden());

        assertEquals(reCommentRepository.findAll().size(), 1);
    }
}
