package com.nssp.bucketops.usecase;

import com.nssp.bucketops.config.S3ClientConfig;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListObjectImpl implements ListObject {

    private S3ClientConfig s3ClientConfig;

    public ListObjectImpl(S3ClientConfig s3ClientConfig) {
        this.s3ClientConfig = s3ClientConfig;
    }

    @Override
    public List<String> list() {
        List<String> listaDeChaves = new ArrayList<>();
        ListObjectsV2Response lista = this.s3ClientConfig.s3Client()
                .listObjectsV2(ListObjectsV2Request.builder()
                .bucket(this.s3ClientConfig.getBucket())
                .build());
        lista.contents().forEach(l -> listaDeChaves.add(l.key()));
        return listaDeChaves;
    }
}
