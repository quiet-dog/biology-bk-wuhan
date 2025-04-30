package com.biology.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "opc")
@Data
public class OpcClientConfig {
    private String host;

    @Bean
    WebClient opcClient(WebClient.Builder builder) {
        return builder.baseUrl(host).build();
    }
}
