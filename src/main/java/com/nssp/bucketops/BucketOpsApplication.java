package com.nssp.bucketops;

import com.nssp.bucketops.usecase.UploadObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/v1")
public class BucketOpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BucketOpsApplication.class, args);
    }

    private UploadObject uploadObject;
    public BucketOpsApplication(UploadObject uploadObject) {
        this.uploadObject = uploadObject;
    }
    @GetMapping("/upload")
    public String get() {
        return this.uploadObject.save();
    }

    @GetMapping("/keys")
    public List<String> getList() {
        return this.uploadObject.list();
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download() {
        ResponseInputStream<GetObjectResponse> responseStream = this.uploadObject.download();
        InputStreamResource resource = new InputStreamResource(responseStream);
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"seinfeld.png\"");
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(responseStream.response().contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
