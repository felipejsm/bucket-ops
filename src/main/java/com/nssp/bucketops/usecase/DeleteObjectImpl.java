package com.nssp.bucketops.usecase;

import com.nssp.bucketops.config.S3ClientConfig;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.ArrayList;

@Component
public class DeleteObjectImpl implements DeleteObject{

    private S3ClientConfig s3ClientConfig;
    private S3Client client;

    public DeleteObjectImpl(S3ClientConfig s3ClientConfig) {
        this.s3ClientConfig = s3ClientConfig;
        this.client = this.s3ClientConfig.s3Client();
    }
    @Override
    public void delete() {
        String objectName = "quotes/george_and_seinfeld_cafe.png";
        ArrayList<ObjectIdentifier> toDelete = new ArrayList<>();
        toDelete.add(ObjectIdentifier.builder().key(objectName).build());
        try {
            DeleteObjectsRequest req = DeleteObjectsRequest.builder()
                    .bucket(this.s3ClientConfig.getBucket())
                    .delete(Delete.builder().objects(toDelete).build())
                    .build();
            this.client.deleteObjects(req);
        } catch(S3Exception s3Exception) {
            System.out.println("uh oh");
        }
        System.out.println("Done!");
    }
}
