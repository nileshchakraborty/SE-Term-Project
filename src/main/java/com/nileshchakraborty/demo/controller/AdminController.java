package com.nileshchakraborty.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nileshchakraborty.demo.dao.Comment;
import com.nileshchakraborty.demo.dao.Post;
import com.nileshchakraborty.demo.dao.User;
import com.nileshchakraborty.demo.repo.CommentRepository;
import com.nileshchakraborty.demo.repo.FriendRepository;
import com.nileshchakraborty.demo.repo.NotificationRepository;
import com.nileshchakraborty.demo.repo.PostRepository;
import com.nileshchakraborty.demo.repo.UserRepository;

@Controller
public class AdminController {
	
	
	@Autowired
	public PostRepository postRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@GetMapping("viewalluser")
	public ModelAndView accessUser(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView("viewallusers");
		List <User> allUser = userRepository.findAll();
		mav.addObject("alluser", allUser);
		return mav;
		
	}
	
	@GetMapping("viewallpost")
	public ModelAndView accessPost(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView("viewallposts");
		List <Post> allUser = postRepository.findAll();
		mav.addObject("allpost", allUser);
		return mav;
		
	}
	
	@GetMapping("/viewusersprofile")
	public ModelAndView viewFriendsProfilePage(HttpServletRequest req,@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView("adminviewusers");
		User u = userRepository.findOne(id);
		List<Post> posts = postRepository.findByUserId(id);
		if(u != null) {
			req.getSession().setAttribute("friend", u);
			mav.addObject("fname", u.getName());
			mav.addObject("fprofileImage", u.getProfileImage());
			mav.addObject("femail", u.getEmail());
			mav.addObject("fdescription", u.getDescription());
			mav.addObject("fposts", posts);
			
		}
		
		return mav;
		
	}
	
	
	@GetMapping(value = "/viewallimage")
	public ModelAndView viewImagePage(HttpServletRequest req, @RequestParam("id") String postId) {
		ModelAndView mav = new ModelAndView("viewadminimage");
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
	
	@GetMapping(value = "/deletepost")
	public ModelAndView deleteImage(HttpServletRequest req, @RequestParam("id") String postId) {
		
		Post p = postRepository.findByPostId(Long.parseLong(postId));
		postRepository.delete(p);
		
		req.getSession().setAttribute("post",p);
		ModelAndView mav = new ModelAndView("viewallposts");
		List <Post> allUser = postRepository.findAll();
		mav.addObject("allpost", allUser);
		return mav;
	}
}
