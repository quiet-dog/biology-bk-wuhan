package com.biology.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Data;
import reactor.core.publisher.Mono;

@Configuration
@ConfigurationProperties(prefix = "koala")
@Data
public class KoalaClientConfig {
    private String host;

    private String username;

    private String password;

    @Bean
    WebClient koalaClient(WebClient.Builder builder) {
        WebClient w = builder.baseUrl(host)
                .defaultHeader("user-agent", "Koala Admin")
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(50 * 1024 * 1024))
                .build();
        return w;
    }
}
