package com.nileshchakraborty.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nileshchakraborty.demo.dao.Comment;
import com.nileshchakraborty.demo.dao.Friends;
import com.nileshchakraborty.demo.dao.Notification;
import com.nileshchakraborty.demo.dao.Post;
import com.nileshchakraborty.demo.dao.User;
import com.nileshchakraborty.demo.repo.CommentRepository;
import com.nileshchakraborty.demo.repo.FriendRepository;
import com.nileshchakraborty.demo.repo.NotificationRepository;
import com.nileshchakraborty.demo.repo.PostRepository;
import com.nileshchakraborty.demo.repo.UserRepository;

@Controller
public class CommentController {
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
	
	@PostMapping("comment")
	public ModelAndView saveComment(HttpServletRequest req,@RequestParam("comment") String comment,@RequestParam("postId") Long postId) {
		
		ModelAndView mav = new ModelAndView("viewfriendsimage");
		User friend = (User) req.getSession().getAttribute("friend");
		User user = (User) req.getSession().getAttribute("user");
		Post post = postRepository.findByPostId(postId);		
		Comment commentObj = new Comment();
		commentObj.setComment(comment);
		commentObj.setPostId(postId);
		commentObj.setUserId(user.getUserid());
		String friendid = null;
		try {
			friendid = friend.getUserid();
		}
		catch(Exception e) {
			friendid = user.getUserid();
		}
		if(friendid == null) {
			friendid = user.getUserid();
			commentObj.setFriendId(user.getUserid());
		}
		else {
			commentObj.setFriendId(friendid);
		}
		commentObj.setUsername(user.getName());
		commentRepository.save(commentObj);
		
		List<Friends> receiverIds = friendsRepository.findByUserId(user.getUserid());
		for(Friends f : receiverIds) {
			Notification n = new Notification();
			n.setPostId(post.getPostId());
			n.setReciverId(f.getFriendId());
			n.setUserId(user.getUserid());
			n.setSentBy(user.getName());
			n.setReceivedBy(f.getFriendName());
			n.setType("comment");
			notificationRepository.save(n);
		}
		List<Comment> comments = commentRepository.findByPostId(post.getPostId());
		
		mav.addObject("comments", comments);
		mav.addObject("specpost", post);
 		return mav;
	}
	
	
}
