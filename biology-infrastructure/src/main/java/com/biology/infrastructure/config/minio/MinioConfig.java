package com.biology.infrastructure.config.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "minio")
@Configuration
@Data
public class MinioConfig {
    String endpoint;
    String accessKey;
    String secretKey;
    String bucketName;
    String targetUrl;

    @Bean
    MinioClient getMinioClient() throws Exception {
        MinioClient minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        return minioClient;
    }

    @Bean
    MinioConfig initMinioConfig() {
        return this;
    }
}
