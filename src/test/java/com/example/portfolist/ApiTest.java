package com.example.portfolist;

import com.example.portfolist.domain.auth.entity.FieldKind;
import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.auth.repository.repository.FieldKindRepository;
import com.example.portfolist.domain.auth.repository.repository.FieldRepository;
import com.example.portfolist.domain.auth.repository.repository.NormalUserRepository;
import com.example.portfolist.domain.auth.repository.repository.UserRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.CertificationRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.RefreshTokenRepository;
import com.example.portfolist.domain.mypage.repository.repository.NotificationRepository;
import com.example.portfolist.domain.portfolio.entity.comment.Comment;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.PortfolioField;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import com.example.portfolist.domain.portfolio.entity.touching.TouchingId;
import com.example.portfolist.domain.portfolio.repository.Container.BoxImageRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxRepository;
import com.example.portfolist.domain.portfolio.repository.Container.BoxTextRepository;
import com.example.portfolist.domain.portfolio.repository.Container.ContainerRepository;
import com.example.portfolist.domain.portfolio.repository.comment.CommentRepository;
import com.example.portfolist.domain.portfolio.repository.comment.ReCommentRepository;
import com.example.portfolist.domain.portfolio.repository.CertificateRepository;
import com.example.portfolist.domain.portfolio.repository.MoreInfoRepository;
import com.example.portfolist.domain.portfolio.repository.PortfolioFieldRepository;
import com.example.portfolist.domain.portfolio.repository.portfolio.PortfolioRepository;
import com.example.portfolist.domain.portfolio.repository.TouchingRepository;
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
import java.time.LocalDate;

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
        boxRepository.deleteAll();
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

    protected ResultActions requestMvc(MockHttpServletRequestBuilder build) throws Exception {
        if (token != null) {
            return mvc.perform(build.contentType(MediaType.APPLICATION_JSON)
                            .header("AUTHORIZATION", "Bearer " + token))
                    .andDo(print());
        }
        return mvc.perform(build
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions requestMvc(MockHttpServletRequestBuilder build, Object obj) throws Exception {
        if (token != null) {
            return mvc.perform(build
                            .content(objectMapper
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(obj))
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("AUTHORIZATION", "Bearer " + token))
                    .andDo(print());
        }
        return mvc.perform(build
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

    public Portfolio createPortfolio(User user, String title, String introduce) {
        return portfolioRepository.save(Portfolio.builder()
                .user(user)
                .title(title)
                .introduce(introduce)
                .isOpen(true)
                .date(LocalDate.now())
                .build());
    }

    public User registerUser(String name, String introduce) {
        return userRepository.save(
                User.builder()
                        .name(name)
                        .introduce(introduce)
                        .build()
        );
    }

    public FieldKind createFieldKind(String content) {
        return fieldKindRepository.save(
                FieldKind.builder()
                        .content(content)
                        .build()
        );
    }

    public PortfolioField createPortfolioField(Portfolio portfolio, FieldKind fieldKind) {
        return portfolioFieldRepository.save(PortfolioField.builder()
                .portfolio(portfolio)
                .fieldKind(fieldKind)
                .build());
    }

    public void createTouching(User user, Portfolio portfolio) {
        touchingRepository.save(new Touching(
                new TouchingId(user.getPk(), portfolio.getPk()), user, portfolio));
    }

    public void createComment(User user, Portfolio portfolio, String content) {
        commentRepository.save(Comment.builder().portfolio(portfolio)
                .user(user)
                .date(LocalDate.now())
                .content(content).build()
        );
    }
}
