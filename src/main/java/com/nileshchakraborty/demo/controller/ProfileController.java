package com.nileshchakraborty.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.nileshchakraborty.demo.dao.Comment;
import com.nileshchakraborty.demo.dao.Friends;
import com.nileshchakraborty.demo.dao.Post;
import com.nileshchakraborty.demo.dao.User;
import com.nileshchakraborty.demo.repo.CommentRepository;
import com.nileshchakraborty.demo.repo.FriendRepository;
import com.nileshchakraborty.demo.repo.PostRepository;
import com.nileshchakraborty.demo.repo.UserRepository;
import com.nileshchakraborty.demo.service.UploadToS3;

@Controller
@SessionAttributes(value = { "user", "friends" })
public class ProfileController {
	
	@Autowired
	private UploadToS3 upload;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FriendRepository friendRepository;
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	@PostMapping(value = "/search")
	public ModelAndView search(HttpServletRequest req,@RequestParam("search") String search) {
		ModelAndView mav = new ModelAndView("search");
		mav.addObject("search", search);
		return new ModelAndView("search");
	}
	
	
	@PostMapping(value = "/createprofile")
	public ModelAndView createProfile(HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("user");
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("user", u);
		mv.setViewName("createprofile");
		return mv;
	}

	@PostMapping(value = "/facebookRedirect")
	public ModelAndView handleRedirect(Model model, @RequestParam(name = "myId") String myId,
			@RequestParam(name = "myName") String myName, @RequestParam(name = "myFriend") String myFriend,
			@RequestParam(name = "myEmail") String myEmail, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView();
		System.out.println(myId + " " + myName + " " + myFriend + " " + myEmail);
		if(myId.equals( "102785203929504")) {
			return new ModelAndView("admin");
		}
		try {

			User u = userRepository.findByUserId(myId);
			if (u != null) {
				myName = u.getName();
				myFriend = u.getFriends();
				myId = u.getUserid();
				myEmail = u.getEmail();

				mv.addObject("user", u);
				mv.setViewName("profilePage");
			} else {
				u = new User();
				u.setUserid(myId.trim());
				u.setName(myName.trim());
				u.setEmail(myEmail.trim());
				String[] splitted = myFriend.split("/");
				List<Friends> list = new ArrayList<>();
				for (int i = 0; i < splitted.length - 1; i++) {
					Friends f = new Friends();
					f.setFriendId(splitted[i]);
					f.setFriendName(splitted[i + 1]);
					f.setUserId(myId.trim());
					list.add(f);

					friendRepository.save(f);
					i++;
				}
				if (!model.containsAttribute("friends"))
					model.addAttribute("friends", list);

				u.setFriends(myFriend);
				if (!model.containsAttribute("user"))
					model.addAttribute("user", u);
				userRepository.save(u);
				mv.addObject("user", u);
				mv.setViewName("createprofile");
			}

			req.getSession().setAttribute("user", u);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@GetMapping(value = "/viewpost")
	public ModelAndView viewImages(HttpServletRequest req) {
		User user = (User) req.getSession().getAttribute("user");
		
		ModelAndView indexPage = new ModelAndView();
		List<Post> posts = (List<Post>) postRepository.findByUserId(user.getUserid());
		indexPage.addObject("user",user);
		indexPage.addObject("posts",posts);
		indexPage.setViewName("viewpost");

		return indexPage;
	}
	
	@GetMapping(value = "/profilePage")
	public ModelAndView profilePage(HttpServletRequest req) {
		User user = (User) req.getSession().getAttribute("user");
		
		ModelAndView indexPage = new ModelAndView();
		List<Post> posts = postRepository.findByUserId(user.getUserid());
		indexPage.addObject(user);
		indexPage.addObject(posts);
		indexPage.setViewName("profilePage");

		return indexPage;
	}
	
	
	@GetMapping(value = "/viewimage")
	public ModelAndView viewImagePage(HttpServletRequest req, @RequestParam("id") String postId) {
		ModelAndView mav = new ModelAndView("viewimage");
		Post post = (Post) req.getSession().getAttribute("post");
		User user = (User) req.getSession().getAttribute("user");
		List<Comment> comments = commentRepository.findByPostId(Long.parseLong(postId));
		mav.addObject("post",post);
		mav.addObject("comments", comments);
		mav.addObject("user", user);
		Post p = postRepository.findByPostId(Long.parseLong(postId));
		mav.addObject("specpost", p);
		return mav;
	}
	
	
	@GetMapping(value = "/")
	public ModelAndView renderPage(HttpServletRequest req) {

		ModelAndView indexPage = new ModelAndView();
		indexPage.setViewName("index");
		

		return indexPage;
	}

	@GetMapping(value = "/adminlogin")
	public ModelAndView adminLoginPage(HttpServletRequest req) {
		ModelAndView loginPage = new ModelAndView();
		loginPage.setViewName("adminlogin");

		return loginPage;
	}

	@GetMapping(value = "/admin")
	public ModelAndView adminPage(HttpServletRequest req) {
		ModelAndView loginPage = new ModelAndView();
		loginPage.setViewName("admin");

		return loginPage;
	}

	@PostMapping(value = "/viewfriendsfriend")
	public ModelAndView viewfriendsfriendPage(@RequestParam(name = "email") String email, HttpServletRequest req) {
	
		ModelAndView mv = new ModelAndView();
		User u = userRepository.findByEmail(email);
		List<Friends> friends = friendRepository.findByUserId(u.getUserid());

		mv.addObject("friends", friends);
		mv.setViewName("friends");

		return mv;
	}
	
	@PostMapping(value = "/viewfriends")
	public ModelAndView friendsPage(@RequestParam(name = "email") String email, HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("user");
		ModelAndView mv = new ModelAndView();
		//User u = userRepository.findByEmail(email);
		List<Friends> friends = friendRepository.findByUserId(u.getUserid());

		mv.addObject("friends", friends);
		mv.setViewName("friends");

		return mv;
	}

	@GetMapping(value = "/post")
	public ModelAndView postPage(HttpServletRequest req) {
		ModelAndView loginPage = new ModelAndView();
		loginPage.setViewName("post");

		return loginPage;
	}

	@PostMapping(value = "/upload")
	public ModelAndView uploadToS3(@RequestParam("myName") String name, @RequestParam("myEmail") String email,
			@RequestParam("description") String description, @RequestParam("file") MultipartFile image, HttpServletRequest req)
			throws IOException {
		User u = (User) req.getSession().getAttribute("user");
		ModelAndView mv = new ModelAndView();
		
		try {
			
			String s3Origin = upload.uploadToS3(image.getOriginalFilename(), image.getInputStream());
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mv.addObject("errormsg", e.getMessage());
			mv.setViewName("error");
			return mv;
		}
	}

	@GetMapping(value = "error")
	public ModelAndView errorHandling(HttpServletRequest req) {
		ModelAndView errorPage = new ModelAndView();
		errorPage.addObject("errormsg", "Error Occured!");
		errorPage.setViewName("error");
		return errorPage;
	}

	@GetMapping(value="logout")
	public ModelAndView logout(HttpServletRequest req) {
		req.getSession().invalidate();
		return new ModelAndView("index");
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
