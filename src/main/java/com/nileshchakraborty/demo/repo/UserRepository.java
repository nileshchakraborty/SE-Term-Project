package com.nileshchakraborty.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nileshchakraborty.demo.dao.User;
@Repository
public interface UserRepository extends CrudRepository<User, String>{
	
	public List<User> findAll();

	public User findByEmail(String email);
	
	public User findByUserId(String id);
}
