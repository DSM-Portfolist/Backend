package com.example.portfolist;

import com.example.portfolist.domain.auth.repository.repository.FieldKindRepository;
import com.example.portfolist.domain.auth.repository.repository.FieldRepository;
import com.example.portfolist.domain.auth.repository.repository.NormalUserRepository;
import com.example.portfolist.domain.auth.repository.repository.UserRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.CertificationRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.RefreshTokenRepository;
import com.example.portfolist.domain.mypage.repository.repository.NotificationRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxImageRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxTextRepository;
import com.example.portfolist.domain.portfolio.repository.Container.ContainerRepository;
import com.example.portfolist.domain.portfolio.repository.comment.CommentRepository;
import com.example.portfolist.domain.portfolio.repository.comment.ReCommentRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.CertificateRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.MoreInfoRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioFieldRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioRepository;
import com.example.portfolist.domain.portfolio.repository.touching.TouchingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.lang.reflect.Field;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class ApiTest extends IntegrationTest{

    @Autowired
    private MockMvc mvc;

    @Setter(AccessLevel.PROTECTED)
    private String token;

    @Autowired
    protected FieldKindRepository fieldKindRepository;
    @Autowired
    protected FieldRepository fieldRepository;
    @Autowired
    protected NormalUserRepository normalUserRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected NotificationRepository notificationRepository;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected ReCommentRepository reCommentRepository;
    @Autowired
    protected BoxImageRepository boxImageRepository;
    @Autowired
    protected BoxRepository boxRepository;
    @Autowired
    protected BoxTextRepository boxTextRepository;
    @Autowired
    protected ContainerRepository containerRepository;
    @Autowired
    protected CertificateRepository certificateRepository;
    @Autowired
    protected MoreInfoRepository moreInfoRepository;
    @Autowired
    protected PortfolioFieldRepository portfolioFieldRepository;
    @Autowired
    protected PortfolioRepository portfolioRepository;
    @Autowired
    protected TouchingRepository touchingRepository;

    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;
    @MockBean
    protected CertificationRepository certificationRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void clear() {
        setToken(null);
        moreInfoRepository.deleteAll();
        boxImageRepository.deleteAll();
        boxTextRepository.deleteAll();
        containerRepository.deleteAll();
        certificateRepository.deleteAll();
        touchingRepository.deleteAll();
        portfolioFieldRepository.deleteAll();
        reCommentRepository.deleteAll();
        commentRepository.deleteAll();
        portfolioRepository.deleteAll();
        notificationRepository.deleteAll();
        fieldRepository.deleteAll();
        fieldKindRepository.deleteAll();
        userRepository.deleteAll();
        normalUserRepository.deleteAll();
    }

    protected ResultActions requestMvc(MockHttpServletRequestBuilder method) throws Exception {
        if (token != null) {
            return mvc.perform(method.contentType(MediaType.APPLICATION_JSON)
                            .header("AUTHORIZATION", "Bearer " + token))
                    .andDo(print());
        }
        return mvc.perform(method
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions requestMvc(MockHttpServletRequestBuilder method, Object obj) throws Exception {
        if (token != null) {
            return mvc.perform(method
                            .content(objectMapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(obj))
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("AUTHORIZATION", "Bearer " + token))
                    .andDo(print());
        }
        return mvc.perform(method
                        .content(objectMapper
                                .registerModule(new JavaTimeModule())
                                .writeValueAsString(obj))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected <T> T inputField(Object objectRequest, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        T request = (T) objectRequest;
        Field field = request.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(request, (T) value);
        return request;
    }

}
