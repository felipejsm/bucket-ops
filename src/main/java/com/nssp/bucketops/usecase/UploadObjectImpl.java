package com.nssp.bucketops.usecase;

import com.nssp.bucketops.config.S3ClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;
import java.io.IOException;

@Component
public class UploadObjectImpl implements UploadObject {
    @Value("classpath:george_and_seinfeld_cafe.png")
    public Resource myFile;

    private S3ClientConfig s3ClientConfig;

    public UploadObjectImpl(S3ClientConfig s3ClientConfig) {
        this.s3ClientConfig = s3ClientConfig;
    }

    @Override
    public String save() {
        try {

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(this.s3ClientConfig.getBucket())
                    .key("quotes/george_and_seinfeld_cafe.png")
                    .contentType("image/jpeg")
                    .build();

            PutObjectResponse response = this.s3ClientConfig.s3Client().putObject(putOb,
                    RequestBody.fromFile(this.myFile.getFile()));
            return response.eTag();
        } catch(S3Exception | IOException e) {

        }
        return null;
    }


}
