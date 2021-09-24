package com.example.portfolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PortfolistApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolistApplication.class, args);
    }

}
