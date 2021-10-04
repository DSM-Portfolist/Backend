package com.example.portfolist;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class TestRedisConfig {

    private static RedisServer redisServer;

    @Value("${spring.redis.port}")
    private Integer port;

    @PostConstruct
    public void runRedis() throws IOException {
        if(redisServer == null) {
            redisServer = new RedisServer(port);
            redisServer.start();
        }
    }

    @PreDestroy
    public void stopRedis() {
        if(redisServer != null) {
            redisServer.stop();
        }
    }
}
