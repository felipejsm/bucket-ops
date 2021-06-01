package com.nssp.bucketops.usecase;

import com.nssp.bucketops.config.S3ClientConfig;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Component
public class DownloadObjectImpl implements DownloadObject {

    private S3ClientConfig s3ClientConfig;
    private S3Client client;

    public DownloadObjectImpl(S3ClientConfig s3ClientConfig) {
        this.s3ClientConfig = s3ClientConfig;
        this.client = this.s3ClientConfig.s3Client();
    }

    @Override
    public ResponseInputStream<GetObjectResponse> download() {
        String objectKey = "quotes/george_and_seinfeld_cafe.png";
        GetObjectRequest req = GetObjectRequest.builder()
                .bucket(this.s3ClientConfig.getBucket())
                .key(objectKey)
                .build();
        ResponseInputStream<GetObjectResponse> s3Object = this.client.getObject(req);
        return s3Object;
    }
}
