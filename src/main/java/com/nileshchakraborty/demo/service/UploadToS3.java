package com.nileshchakraborty.demo.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class UploadToS3 {
	@Value("${accessKey}")
	String accessKey;

	@Value("${secretKey}")
	String secretKey;

	private static BasicAWSCredentials credentials = null;

	@PostMapping(value = "/upload")
	public String uploadToS3(String filename, InputStream inputStream) throws IOException {
		credentials = new BasicAWSCredentials(accessKey, secretKey);
		try {
			AmazonS3 s3client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();

			PutObjectRequest putReq = new PutObjectRequest("demotest-nilesh", filename, inputStream, new ObjectMetadata())
					.withCannedAcl(CannedAccessControlList.PublicRead);
			s3client.putObject(putReq);
			String fileURL = "http://" + "demotest-nilesh" + ".s3.amazonaws.com/" + filename;
			return fileURL;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
