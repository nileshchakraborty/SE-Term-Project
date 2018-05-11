package com.nileshchakraborty.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Base64.Decoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.nileshchakraborty.demo.dao.Notification;
import com.nileshchakraborty.demo.dao.Post;
import com.nileshchakraborty.demo.dao.User;
import com.nileshchakraborty.demo.repo.FriendRepository;
import com.nileshchakraborty.demo.repo.NotificationRepository;
import com.nileshchakraborty.demo.repo.PostRepository;
import com.nileshchakraborty.demo.repo.UserRepository;
import com.nileshchakraborty.demo.service.UploadToS3;

@Controller
public class PostController {

	@Autowired
	private UploadToS3 upload;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FriendRepository friendRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;

	@GetMapping(value = "/createpost")
	public ModelAndView recordAudio() {
		return new ModelAndView("post");
	}

	@PostMapping(value = "/analysepost")
	public ModelAndView analyze(HttpServletRequest req,@RequestParam("takepicture") String image) throws IOException,Exception {
		ModelAndView mv = new ModelAndView();
		User u = null;
		Post p = null;
		try{
			u = (User) req.getSession().getAttribute("user");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("==============here============");
		ModelAndView succ = new ModelAndView("viewpost");
		if(image.isEmpty()) {
			throw new Exception("image is null");
		}
		
		Decoder decode = java.util.Base64.getDecoder();
		System.out.println("========>"+image);
		byte[] decodeByte = decode.decode(image.split(",")[1]);
		System.out.println("=========>SEE THIS"+decodeByte);
		//String name = u.getName().concat("_"+System.nanoTime());
		
		String name = ""+System.nanoTime();
		File dir = new File("data/image/");
		if (!dir.exists()) dir.mkdirs(); 
		File file = new File(dir+"/"+name+".png");
		
		try(FileOutputStream fos = new FileOutputStream(file)){
			fos.write(decodeByte);
			fos.close();
		}
		try {
			if (u == null) throw new NullPointerException("User session not defined");
			
			String uid = u.getUserid(); 
			String post = "1";
			String timestamp = ""+ System.nanoTime();
			String s3Origin = upload.uploadToS3(uid+"_"+post+"_"+timestamp+".png", new FileInputStream(file));
			p = new Post();
			p.setUserId(u.getUserid());
			p.setImageUrl(s3Origin);
			req.getSession().setAttribute("post", p);
			mv.addObject("user", u);
			mv.addObject("post",p);
			mv.setViewName("postaudio");
			return mv;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mv.addObject("errormsg", e.getMessage());
			mv.setViewName("error");
			return mv;
		}
		
		
	}
	@PostMapping(value = "/analyseaudiopost")
	public ModelAndView analyzeAudio(HttpServletRequest req,@RequestParam("record") String record) throws IOException,Exception {
		ModelAndView mv = new ModelAndView();
		User u = null;
		Post p = null;
		try{
			u = (User) req.getSession().getAttribute("user");
			p = (Post) req.getSession().getAttribute("post");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("==============here============");
		ModelAndView succ = new ModelAndView("viewpost");
		if(record.isEmpty()) {
			throw new Exception("record is null");
		}
		
		Decoder decode = java.util.Base64.getDecoder();
		System.out.println("========>"+record);
		byte[] decodeByte = decode.decode(record.split(",")[1]);
		System.out.println("=========>SEE THIS"+decodeByte);
		//String name = u.getName().concat("_"+System.nanoTime());
		
		String name = ""+System.nanoTime();
		File dir = new File("data/audio/");
		if (!dir.exists()) dir.mkdirs();
		File file = new File(dir+"/"+name+".webm");
		
		try(FileOutputStream fos = new FileOutputStream(file)){
			fos.write(decodeByte);
			fos.close();
		}
		try {
			if (u == null) throw new NullPointerException("User session not defined");
			String uid = u.getUserid();
			
			String timestamp = ""+ System.nanoTime();
			String s3Origin = upload.uploadToS3(uid+"_"+timestamp+".webm", new FileInputStream(file));
			
			
			p.setAudioUrl(s3Origin);
			//postRepository.save(p);
			mv.addObject("user", u);
			mv.addObject("post",p);
			mv.setViewName("posttext");
			return mv;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mv.addObject("errormsg", e.getMessage());
			mv.setViewName("error");
			return mv;
		}
		
		
	}
	@PostMapping(value = "/analysetextpost")
	public ModelAndView analyzeText(HttpServletRequest req,@RequestParam("text") String text) throws IOException,Exception {
		ModelAndView mv = new ModelAndView();
		User u = null;
		Post p = null;
		try{
			u = (User) req.getSession().getAttribute("user");
			p = (Post) req.getSession().getAttribute("post");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("==============here============");
		ModelAndView succ = new ModelAndView("viewpost");
		/*if(text.isEmpty()) {
			throw new Exception("text is null");
		}*/
		try {
			
			p.setText(text);
			postRepository.save(p);
			List<Friends> receiverIds = friendRepository.findByUserId(u.getUserid());
			for(Friends f : receiverIds) {
				Notification n = new Notification();
				n.setPostId(p.getPostId());
				n.setReciverId(f.getFriendId());
				n.setUserId(u.getUserid());
				n.setSentBy(u.getName());
				n.setReceivedBy(f.getFriendName());
				n.setType("post");
				notificationRepository.save(n);
			}
			
			mv.addObject("user", u);
			mv.addObject("post",p);
			mv.setViewName("success");
			return mv;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mv.addObject("errormsg", e.getMessage());
			mv.setViewName("error");
			return mv;
		}
		
		
	}
}
