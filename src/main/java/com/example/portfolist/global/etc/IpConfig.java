package com.example.portfolist.global.etc;

import com.example.portfolist.domain.auth.exception.UserNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class IpConfig {

    @Bean
    public String localServerIp() {
        try {
            InetAddress ip = java.net.InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            throw new UserNotFoundException();
        }
    }

}
