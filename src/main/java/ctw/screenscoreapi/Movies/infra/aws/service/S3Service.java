package ctw.screenscoreapi.Movies.infra.aws.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
    private S3Client s3Client;
    private String bucketName;

    public S3Service(S3Client s3Client, @Value("${aws.s3.bucketname}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public String putObject(MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        PutObjectRequest configRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        RequestBody requestBody = RequestBody.fromBytes(file.getBytes());

        s3Client.putObject(configRequest, requestBody);

        return key;
    }
}
