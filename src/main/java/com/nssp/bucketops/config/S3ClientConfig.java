package com.nssp.bucketops.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.net.URI;

@Configuration
@Data
public class S3ClientConfig {
    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.uri}")
    private URI uri;

    @Value("${aws.region}")
    private Region region;

    @Bean
    public S3Client s3Client() {
        S3ClientBuilder s3Client = S3Client.builder();
        if(this.uri != null) {
            s3Client.endpointOverride(uri);
        }
        return s3Client.region(region)
                .build();
    }
}
