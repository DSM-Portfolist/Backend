package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.constant.UserConstant;
import com.example.portfolist.domain.portfolio.service.FileService;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileControllerTest extends ApiTest {

    NormalUser normalUser;
    User user;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        normalUser = NormalUser.builder()
                .email(UserConstant.EMAIL)
                .password(passwordEncoder.encode(UserConstant.PASSWORD))
                .build();
        normalUserRepository.save(normalUser);
        user = User.builder()
                .name(UserConstant.NAME)
                .introduce(UserConstant.INTRODUCE)
                .normalUser(normalUser)
                .build();
        userRepository.save(user);
    }

    @MockBean
    FileService fileService;

    @Test
    public void 파일_업로드_201() throws Exception {
        //given
        auth();
        String filePath = "src/test/resources/d.jpg";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "hello.pn",
                "image/png",
                new FileInputStream("src/test/resources/d.jpg")
        );

        requestMvc(MockMvcRequestBuilders.multipart("/file")
            .file(file))
        .andExpect(status().isCreated());
    }

    private void auth() {
        setToken(jwtTokenProvider.generateAccessToken(user.getId()));
    }
}