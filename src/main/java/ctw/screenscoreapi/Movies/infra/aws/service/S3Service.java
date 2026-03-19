package ctw.screenscoreapi.Movies.infra.aws.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
public class S3Service {
    private S3Client s3Client;
    private String bucketName;
    private S3Presigner s3Presigner;

    public S3Service(S3Client s3Client, @Value("${aws.s3.bucketname}") String bucketName, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.s3Presigner = s3Presigner;
    }

    public String putObject(MultipartFile file) throws IOException {
        String posterKey = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        PutObjectRequest configRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(posterKey)
                .build();

        RequestBody requestBody = RequestBody.fromBytes(file.getBytes());

        s3Client.putObject(configRequest, requestBody);

        return posterKey;
    }

    public void deleteObject(String posterKey) {
        DeleteObjectRequest configRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(posterKey)
                .build();

        s3Client.deleteObject(configRequest);
    }

    public String getObject(String posterKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(posterKey)
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toString();
    }
}
