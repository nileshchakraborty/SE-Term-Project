package com.nileshchakraborty.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nileshchakraborty.demo.dao.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, String>{
	public List<Notification> findByReciverId(String id);
	
	
}
