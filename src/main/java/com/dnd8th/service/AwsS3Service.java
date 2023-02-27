package com.dnd8th.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dnd8th.error.exception.user.ImageUploadFailedException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.s3.bucket}")
    private String imageBucket;

    public String uploadImageToS3(MultipartFile imageFile, String userEmail) {
        // 디렉토리 이름은 userEmail
        Date date = new Date();
        String imageKey = userEmail + '/' + date.getTime();

        // 메타 데이터 추출
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(imageFile.getSize());
        objectMetadata.setContentType(imageFile.getContentType());

        // s3 upload
        try (InputStream inputStream = imageFile.getInputStream()) {
            amazonS3Client.putObject(
                    new PutObjectRequest(imageBucket, imageKey, inputStream,
                            objectMetadata)
                            .withCannedAcl(CannedAccessControlList.Private));
        } catch (IOException e) {
            throw new ImageUploadFailedException();
        }

        // file path get
        String snapshotImageFilePath = String.valueOf(amazonS3Client.getUrl(imageBucket, imageKey));

        return snapshotImageFilePath;
    }
}
