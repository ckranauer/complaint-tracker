package com.compalinttracker.claimdb.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AmazonConfig {

    @Value("${aws.access}")
    private String accKey;

    @Value("${aws.security}")
    private String secKey;


    @Bean
    public AmazonS3 s3(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                accKey,
                secKey
        );

        return AmazonS3ClientBuilder
                .standard()
                .withRegion("eu-west-2")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}
