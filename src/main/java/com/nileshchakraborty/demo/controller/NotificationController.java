package com.nileshchakraborty.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nileshchakraborty.demo.dao.Notification;
import com.nileshchakraborty.demo.dao.Post;
import com.nileshchakraborty.demo.dao.User;
import com.nileshchakraborty.demo.repo.CommentRepository;
import com.nileshchakraborty.demo.repo.FriendRepository;
import com.nileshchakraborty.demo.repo.NotificationRepository;
import com.nileshchakraborty.demo.repo.PostRepository;
import com.nileshchakraborty.demo.repo.UserRepository;

@Controller
public class NotificationController {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private FriendRepository friendRepository;
	
	@GetMapping("notification")
	public ModelAndView viewNotificaitons(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView("viewnotification");
		User user = (User) req.getSession().getAttribute("user");
		List<Notification> notifications = notificationRepository.findByReciverId(user.getUserid());
		
		mav.addObject("notifications", notifications);
		
		return mav;
	}
	

}
