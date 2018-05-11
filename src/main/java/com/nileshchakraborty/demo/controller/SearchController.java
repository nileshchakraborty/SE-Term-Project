package com.nileshchakraborty.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nileshchakraborty.demo.dao.Friends;
import com.nileshchakraborty.demo.dao.Post;
import com.nileshchakraborty.demo.repo.CommentRepository;
import com.nileshchakraborty.demo.repo.FriendRepository;
import com.nileshchakraborty.demo.repo.NotificationRepository;
import com.nileshchakraborty.demo.repo.PostRepository;
import com.nileshchakraborty.demo.repo.UserRepository;

@Controller
public class SearchController {

	@Autowired
	public FriendRepository friendsRepository;

	@Autowired
	public PostRepository postRepository;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public CommentRepository commentRepository;

	@Autowired
	public NotificationRepository notificationRepository;

	@GetMapping("/search")
	public ModelAndView search(HttpServletRequest req, @RequestParam("search") String search) {
		ModelAndView mav = new ModelAndView("search");
		
		List<Friends> friends = friendsRepository.findAll();

		for (Friends friend : friends) {
			if (friend.getFriendName().contains(search)) {
				mav.addObject("friend", friend);
				List<Post> post = postRepository.findByUserId(friend.getFriendId());
				mav.addObject("posts", post);
			}
		}

		return mav;

	}
}
