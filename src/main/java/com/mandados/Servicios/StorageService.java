package com.mandados.Servicios;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
// import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class StorageService {
    
    // @Value("mdmstorage")
    private String bucketName = "mdmstorage";

    @Autowired
    private AmazonS3 s3client;

        // @Value("AKIAIW7PKJN54Z72KURA")
        private String accessKey = "AKIAJV3GASXELZ5GB5TQ"; 
      
        // @Value("mBvSBhLB6mM2X7yzrrQLlYmQ36a/Q7/1sRKkCzBq")
        private String accessSecret = "qp5eBufXmAURvkY5cr/WFPnYB65hS9Tp/JmXlb45";
          
        // @Value("us-east-2")
        private String region = "us-east-2";

        public String uploadFileS3_2(MultipartFile file, String carpeta) {
            String bucketCarpeta = bucketName+carpeta;
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
            s3client = AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();
           
            // System.out.println("==========AQUI============"); 
            // System.out.println(bucketCarpeta); 
            // System.out.println(s3client.getRegion());
            // System.out.println("ACCES KEY:"+accessKey);
            // System.out.println("accessSecret: "+ accessSecret);
            // System.out.println("region:" +region);
            // System.out.println("BK: "+bucketName);
            File fileObj = convertMultiPartFileToFile(file);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            System.out.println("Uploading a new object to S3 from a file\n");
            s3client.putObject(new PutObjectRequest(bucketCarpeta, fileName, fileObj));
            s3client.setObjectAcl(bucketCarpeta, fileName, CannedAccessControlList.PublicRead);

            fileObj.delete();
            return fileName;
        }

    public String uploadFileS3(MultipartFile file) {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        s3client = AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .build();
       
        System.out.println("==========AQUI============");        
        System.out.println(s3client.getRegion());
        System.out.println("ACCES KEY:"+accessKey);
        System.out.println("accessSecret: "+ accessSecret);
        System.out.println("region:" +region);
        System.out.println("BK: "+bucketName);
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        System.out.println("Uploading a new object to S3 from a file\n");
        s3client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return fileName;
    }

    public String uploadS3(String fileName, File file){
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
        return "EXITO";
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String deleteFile(String fileName) {
        s3client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            System.out.print("Error converting multipartFile to file");
        }
        return convertedFile;
    }
}
