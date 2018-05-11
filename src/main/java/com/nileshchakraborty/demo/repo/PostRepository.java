package com.nileshchakraborty.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nileshchakraborty.demo.dao.Post;


@Repository
public interface PostRepository extends CrudRepository<Post, String>{
	public List<Post> findAll();
	public Post findByPostId(Long id);
	public List<Post> findByUserId(String id);
}
