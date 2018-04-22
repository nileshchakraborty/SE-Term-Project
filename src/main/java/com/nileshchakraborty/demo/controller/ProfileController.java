package com.nileshchakraborty.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.nileshchakraborty.demo.dao.Friends;
import com.nileshchakraborty.demo.dao.User;
import com.nileshchakraborty.demo.repo.FriendRepository;
import com.nileshchakraborty.demo.repo.UserRepository;

@Controller
@SessionAttributes(value={"user","friends"})
public class ProfileController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FriendRepository friendRepository;

	@Value("${accessKey}")
	String accessKey;

	@Value("${secretKey}")
	String secretKey;

	private static BasicAWSCredentials credentials = null;

	@PostMapping(value = "/createprofile")
	public ModelAndView createProfile(@ModelAttribute("user") User u,@RequestParam(name="myEmail") String email) {
		ModelAndView mv = new ModelAndView();
		u = userRepository.findByEmail(email);
		mv.addObject("user", u);
		mv.setViewName("createprofile");
		return mv;
	}

	@GetMapping(path = "/viewAllUsers")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@PostMapping(value = "/facebookRedirect")
	public ModelAndView handleRedirect(Model model,@RequestParam(name = "myId") String myId,
			@RequestParam(name = "myName") String myName, @RequestParam(name = "myFriend") String myFriend,
			@RequestParam(name = "myEmail") String myEmail, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView();
		System.out.println(myId + " " + myName + " " + myFriend + " " + myEmail);

		try {
			
				User u = userRepository.findByUserId(myId);
				if (u != null) {
					mv.addObject("user", u);
					mv.setViewName("profilePage");
				} else {
					u = new User();
					u.setUserid(myId);
					u.setName(myName);
					u.setEmail(myEmail);
					String[] splitted = myFriend.split("/");
					for (int i = 0; i < splitted.length - 1; i++) {
						Friends f = new Friends();
						f.setFriendId(splitted[i]);
						f.setFriendName(splitted[i + 1]);
						f.setUserId(myId);
						if(!model.containsAttribute("friends"))
							model.addAttribute("friends", f);
						
						friendRepository.save(f);
						i++;
					}
					u.setFriends(myFriend);
					if(!model.containsAttribute("user")) 
						model.addAttribute("user",u);
					userRepository.save(u);
					mv.addObject("user", u);
					mv.setViewName("createprofile");
				}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@GetMapping(value = "/profilePage")
	public ModelAndView profilePage() {

		ModelAndView indexPage = new ModelAndView();

		indexPage.setViewName("profilePage");

		return indexPage;
	}

	@GetMapping(value = "/")
	public ModelAndView renderPage() {

		ModelAndView indexPage = new ModelAndView();
		indexPage.setViewName("index");
		credentials = new BasicAWSCredentials(accessKey, secretKey);

		return indexPage;
	}

	@GetMapping(value = "/adminlogin")
	public ModelAndView adminLoginPage() {
		ModelAndView loginPage = new ModelAndView();
		loginPage.setViewName("adminlogin");

		return loginPage;
	}

	@GetMapping(value = "/admin")
	public ModelAndView adminPage() {
		ModelAndView loginPage = new ModelAndView();
		loginPage.setViewName("admin");

		return loginPage;	
	}

	@PostMapping(value = "/viewfriends")
	public ModelAndView friendsPage(@ModelAttribute("user") User u,@ModelAttribute("friends") Friends f, @RequestParam(name="email") String email) {
		ModelAndView mv = new ModelAndView();
		
		List<Friends> friends = friendRepository.findByUserId(u.getUserid());
		
		mv.addObject("friends", friends);
		mv.setViewName("friends");

		return mv;
	}

	@GetMapping(value = "/post")
	public ModelAndView postPage() {
		ModelAndView loginPage = new ModelAndView();
		loginPage.setViewName("post");

		return loginPage;
	}

	@PostMapping(value = "/upload")
	public ModelAndView uploadToS3(@RequestParam("myName") String name, @RequestParam("myEmail") String email,
			@RequestParam("description") String description, @RequestParam("file") MultipartFile image)
			throws IOException {
		ModelAndView mv = new ModelAndView();
		User u = userRepository.findByEmail(email);
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();

		try {
			PutObjectRequest putReq = new PutObjectRequest("demotest-nilesh", image.getOriginalFilename(),
					image.getInputStream(), new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead);
			s3client.putObject(putReq);
			String s3Origin = "http://" + "demotest-nilesh" + ".s3.amazonaws.com/" + image.getOriginalFilename();
			u.setDescription(description);
			u.setName(name);
			u.setEmail(email);
			u.setProfileImage(s3Origin);
			userRepository.save(u);
			List<Friends> f = friendRepository.findByUserId(u.getUserid());
			
			mv.addObject("user", u);
			mv.addObject("friends", f);
			
			mv.setViewName("profilePage");
			return mv;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mv.addObject("errormsg", e.getMessage());
			mv.setViewName("error");
			return mv;
		}
	}

	@GetMapping(value = "error")
	public ModelAndView errorHandling() {
		ModelAndView errorPage = new ModelAndView();
		errorPage.addObject("errormsg", "Error Occured!");
		errorPage.setViewName("error");
		return errorPage;
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {

			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}

		};

		tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
		return tomcat;
	}

	private Connector initiateHttpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);

		return connector;
	}
}
