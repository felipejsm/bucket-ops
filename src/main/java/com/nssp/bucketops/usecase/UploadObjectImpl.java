package com.nssp.bucketops.usecase;

import com.nssp.bucketops.config.S3ClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<String> list() {
        List<String> listaDeChaves = new ArrayList<>();
        Region region = Region.SA_EAST_1;
        String bucketName = "seinfeld";
        String objectKey = "quotes";
        URI uri = URI.create("http://localhost:4566");

        S3Client s3Client = S3Client.builder()
                .endpointOverride(uri)
                .region(region)
                .build();

        ListObjectsV2Response lista = s3Client.listObjectsV2(ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build());
        lista.contents().forEach(l -> listaDeChaves.add(l.key()));
        return listaDeChaves;
    }

    @Override
    public ResponseInputStream<GetObjectResponse>  download() {
        Region region = Region.SA_EAST_1;
        String bucketName = "seinfeld";
        String objectKey = "quotes/george_and_seinfeld_cafe.png";
        URI uri = URI.create("http://localhost:4566");

        S3Client s3Client = S3Client.builder()
                .endpointOverride(uri)
                .region(region)
                .build();
        GetObjectRequest req = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(req);
        return s3Object;
    }


}
