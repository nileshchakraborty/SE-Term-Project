package com.nileshchakraborty.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nileshchakraborty.demo.dao.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, String>{
	public List<Comment> findAll();
	public List<Comment> findByPostId(Long id);
	public List<Comment> findByUserId(String id);
	public List<Comment> findByFriendId(String id);
}
