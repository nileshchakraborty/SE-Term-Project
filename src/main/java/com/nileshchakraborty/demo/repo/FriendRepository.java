package com.nileshchakraborty.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nileshchakraborty.demo.dao.Friends;

@Repository
public interface FriendRepository extends CrudRepository<Friends, String>{
	
	public List<Friends> findByUserId(String id);
	
}
