package com.nileshchakraborty.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nileshchakraborty.demo.dao.Friends;
import com.nileshchakraborty.demo.dao.Post;
import com.nileshchakraborty.demo.dao.User;
import com.nileshchakraborty.demo.repo.FriendRepository;
import com.nileshchakraborty.demo.repo.PostRepository;
import com.nileshchakraborty.demo.repo.UserRepository;

@Controller
public class FriendsController {

	@Autowired
	public FriendRepository friendsRepository;
	
	@Autowired
	public PostRepository postRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@GetMapping("/viewfriendsprofile")
	public ModelAndView viewFriendsProfilePage(HttpServletRequest req,@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView("friendsprofile");
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
	

	@GetMapping(value = "/viewfriendsimage")
	public ModelAndView viewFriendsImagePage(HttpServletRequest req, @RequestParam("id") String postId) {
		ModelAndView mav = new ModelAndView("viewfriendsimage");
		//Post post = (Post) req.getSession().getAttribute("post");
		
		//mav.addObject("post",post);
		Post p = postRepository.findByPostId(Long.parseLong(postId));
		mav.addObject("specpost", p);
		return mav;
	}
	
	
}
