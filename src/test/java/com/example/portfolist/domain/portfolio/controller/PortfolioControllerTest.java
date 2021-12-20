package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.ApiTest;
import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.NormalUser;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.constant.UserConstant;
import com.example.portfolist.domain.portfolio.dto.request.*;
import com.example.portfolist.global.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PortfolioControllerTest extends ApiTest {

    NormalUser normalUser;
    User user;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
    
    @Test
    public void 포트폴리오_리스트_200() throws Exception {
        auth();

        requestMvc(get("/portfolio/list")
                .queryParam("query", "")
                .queryParam("searchType", "user"))
                .andExpect(status().isOk());
    }

    @Test
    public void 포트폴리오_상세보기_200() throws Exception {
        auth();
        long pk = createPortfolio(user, "title", "introduce").getPk();

        requestMvc(get("/portfolio/" + pk))
                .andExpect(status().isOk());
    }

    @Test
    public void 포트폴리오_상세보기_404() throws Exception {
        auth();
        long pk = createPortfolio(user, "title", "introduce").getPk();

        requestMvc(get("/portfolio/" + (pk+1)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void 포트폴리오_생성_201() throws Exception {
        auth();

        fieldKindRepository.save(FieldKind.builder()
                .content("WEB")
                .build());
        fieldKindRepository.save(FieldKind.builder()
                .content("APP")
                .build());

        requestMvc(post("/portfolio"),
                new PortfolioRequest(
                        "1",
                        "link",
                        asList(1,2),
                        asList(new CertificateRequest("자격증", asList("c1", "c2")),
                                new CertificateRequest("학력", asList("c3", "c4"))
                        ),
                        asList(new MoreInfoRequest("name1", "content1"),
                                new MoreInfoRequest("name2", "content2")),
                        "introduce",
                        "title",
                        "file.png",
                        true,
                        "thumbnail.png",
                        asList(new ContainerRequest("containerTitle1",
                                asList("containerImg1", "containerImg2"),
                                asList(new ContainerTextRequest("boxTitle1", "boxContent1"),
                                        new ContainerTextRequest("boxTitle2", "boxContent2"))),
                                new ContainerRequest("containerTitle2",
                                        asList("containerImg3", "containerImg4"),
                                        asList(new ContainerTextRequest("boxTitle3", "boxContent3"),
                                                new ContainerTextRequest("boxTitle4", "boxContent4")))
                        )
        )).andExpect(status().isCreated());

        assertEquals(portfolioRepository.findAll().size(), 1);
    }

    @Test
    public void 포트폴리오_삭제_204() throws Exception {
        auth();

        long pk = createPortfolio(user, "title", "introduce").getPk();

        requestMvc(delete("/portfolio/" + pk))
            .andExpect(status().isNoContent());

        assertEquals(portfolioRepository.findAll().size(), 0);
    }

    @Test
    public void 포트폴리오_업데이트_200() throws Exception {
        //given
        auth();

        long pk = createPortfolio(user, "title", "introduce").getPk();

        //when
        requestMvc(put("/portfolio/" + pk),
                new PortfolioRequest(
                        "1",
                        "link",
                        Collections.emptyList(),
                        asList(new CertificateRequest("자격증", asList("c1", "c2")),
                                new CertificateRequest("학력", asList("c3", "c4"))
                        ),
                        asList(new MoreInfoRequest("name1", "content1"),
                                new MoreInfoRequest("name2", "content2")),
                        "updated introduce",
                        "updated title",
                        "file.png",
                        true,
                        "thumbnail.png",
                        Collections.emptyList()
                ))
                .andExpect(status().isOk());

        //then
        assertEquals(portfolioRepository.findById(pk)
                .orElseThrow().getTitle(), "updated title");

    }

    @Test
    public void 최근_포트폴리오_보기_200() throws Exception {
        auth();
        createPortfolio(user, "title1", "introduce1");
        createPortfolio(user, "title2", "introduce2");

        requestMvc(get("/portfolio/recent")
                .queryParam("page", "0")
                .queryParam("size", "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void 이달_포트폴리오_보기_200() throws Exception {
        auth();
        createPortfolio(user, "title1", "introduce1");
        createPortfolio(user, "title2", "introduce2");

        requestMvc(get("/portfolio/month"))
                .andExpect(status().isOk());
    }

    @Test
    public void 유저_포트폴리오_보기_200() throws Exception {
        auth();
        createPortfolio(user, "title1", "introduce1");
        createPortfolio(user, "title2", "introduce2");

        requestMvc(get("/portfolio/user/" + user.getPk()))
                .andExpect(status().isOk());
    }

    @Test
    public void 터칭_포트폴리오_보기_200() throws Exception {
        auth();
        createPortfolio(user, "title1", "introduce1");
        createPortfolio(user, "title2", "introduce2");

        requestMvc(get("/portfolio/user/" + user.getPk() + "/touching"))
                .andExpect(status().isOk());
    }

    private void auth() {
        setToken(jwtTokenProvider.generateAccessToken(user.getPk()));
    }
}