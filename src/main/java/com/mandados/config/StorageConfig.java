package com.mandados.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
    // @Value("AKIAIW7PKJN54Z72KURA")
    private String accessKey = "AKIAJV3GASXELZ5GB5TQ"; 
      
    // @Value("mBvSBhLB6mM2X7yzrrQLlYmQ36a/Q7/1sRKkCzBq")
    private String accessSecret = "qp5eBufXmAURvkY5cr/WFPnYB65hS9Tp/JmXlb45";
      
    // @Value("us-east-2")
    private String region = "us-east-2";
      
    @Bean
    public AmazonS3 s3client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        return AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .build();
       
    }

}
