package com.alkemy.ong.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonS3Service {
    private AmazonS3 s3client;

    @Value("${aws.endpointUrl}")
    private String endpointUrl;

    @Value("${aws.bucket_name}")
    private String bucketName;

    @Value("${aws.access_key}")
    private String accessKey;

    @Value("${aws.secret_access_key}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    //Convert multipartfile to file
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    //Generate name for the file
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    //Upload file to the bucket
    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    //Method to be call from Controller
    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    // S3 bucket cannot delete file by url. It requires a bucket name and a file name
    public String deleteFile(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        if(existsOnBucket(fileName)){
            s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
            return "Successfully deleted";
        }
        return "File not found, check the name and try it again.";
    }

    public boolean existsOnBucket(String filename){
        Boolean result;
        try {
            S3Object obj = s3client.getObject(bucketName, filename);
            result = true;
        } catch(AmazonS3Exception e){
            result = false;
        }
        return result;
    }
}
