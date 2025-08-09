package com.biology.infrastructure.config.renti;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Data;

@ConfigurationProperties(prefix = "renti")
@Configuration
@Data
public class RenTiConfig {
    String host;
    String topic;
    String http;
    String uri;

    @Bean
    WebClient renTiClient(WebClient.Builder builder) {
        return builder.baseUrl(http).build();
    }
}
