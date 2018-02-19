package com.nileshchakraborty.demo;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Controller
public class ProfileController {
	private static BasicAWSCredentials credentials = new BasicAWSCredentials("secret key",
			"secret key");

	@GetMapping(value = "/")
	public ModelAndView renderPage() {
		ModelAndView indexPage = new ModelAndView();
		indexPage.setViewName("index");
		return indexPage;
	}

	@PostMapping(value = "/upload")
	public ModelAndView uploadToS3(@RequestParam("file") MultipartFile image) throws IOException {
		ModelAndView profilePage = new ModelAndView();

		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();

		try {
			PutObjectRequest putReq = new PutObjectRequest("demotest-nilesh", image.getOriginalFilename(),
					image.getInputStream(), new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead);
			s3client.putObject(putReq);
			String s3Origin = "http://" + "demotest-nilesh" + ".s3.amazonaws.com/" + image.getOriginalFilename();
			profilePage.addObject("image", s3Origin);
			profilePage.setViewName("profilePage");
			return profilePage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			profilePage.addObject("errormsg", e.getMessage());
			profilePage.setViewName("error");
			return profilePage;
		}
	}
	
	@GetMapping(value = "/error")
	public ModelAndView errorHandling() {
		ModelAndView errorPage = new ModelAndView();
		errorPage.addObject("errormsg", "Error Occured!");
		errorPage.setViewName("error");
		return errorPage;
	}
}
