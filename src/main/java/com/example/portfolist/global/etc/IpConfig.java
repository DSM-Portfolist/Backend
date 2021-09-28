package com.example.portfolist.global.etc;

import com.example.portfolist.global.error.exception.NotFoundLocalServerIpException;
import com.example.portfolist.global.etc.dto.LocalServerIp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class IpConfig {

    @Bean
    public LocalServerIp localServerIp() {
        try {
            InetAddress ip = java.net.InetAddress.getLocalHost();
            return new LocalServerIp(ip.getHostAddress());
        } catch (UnknownHostException e) {
            throw new NotFoundLocalServerIpException();
        }
    }

}
