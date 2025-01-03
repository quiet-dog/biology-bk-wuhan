package com.biology.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "gosip")
@Data
public class GosipClientConfig {
    // 获取配置文件中的url
    private String host;

    private String token;

    @Bean
    WebClient gosipClient(WebClient.Builder builder) {
        return builder.baseUrl(host).defaultHeader("Authorization", token).build();
    }
}