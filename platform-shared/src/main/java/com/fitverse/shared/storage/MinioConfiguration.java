package com.fitverse.shared.storage;

import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioConfiguration.MinioProperties.class)
public class MinioConfiguration {

    private final MinioProperties properties;

    public MinioConfiguration(MinioProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }

    @ConfigurationProperties(prefix = "fitverse.minio")
    public static class MinioProperties {

        private String endpoint = "http://localhost:9000";
        private String accessKey = "minio";
        private String secretKey = "minio123";
        private String bucket = "fitverse";

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }
    }
}
