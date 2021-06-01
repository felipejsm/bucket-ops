package com.nssp.bucketops.usecase;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public interface DownloadObject {
    public ResponseInputStream<GetObjectResponse> download();
}
