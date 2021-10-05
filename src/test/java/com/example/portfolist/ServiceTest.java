package com.example.portfolist;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

@ExtendWith(SpringExtension.class)
public class ServiceTest {

    protected <T> T inputField(Object objectRequest, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        T request = (T) objectRequest;
        Field field = request.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(request, (T) value);
        return request;
    }

}
