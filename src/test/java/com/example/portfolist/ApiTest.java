package com.example.portfolist;

import com.example.portfolist.domain.auth.repository.repository.redis.CertificationRepository;
import com.example.portfolist.domain.auth.repository.repository.redis.RefreshTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Setter;
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

    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;
    @MockBean
    protected CertificationRepository certificationRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
