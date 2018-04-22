package com.nileshchakraborty.demo.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {

	@Id
	//@ManyToOne
//    @JoinColumn(name="userid", nullable=false)
	@Column(name="userId")
	private String userId;
	@Column(name = "name")
	private String name;
	@Column(name = "profileImage")
	private String profileImage;
	@Column(name = "friends")
	private String friends;
	@Column(name = "email")
	private String email;
	@Column(name = "description")
	private String description;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserid() {
		return userId;
	}
	public void setUserid(String userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getFriends() {
		return friends;
	}
	public void setFriends(String friends) {
		this.friends = friends;
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/*public void addToFriend(Friends friends) {
		if(this.friends == null) {
			this.friends = new HashSet<Friends>();
		}
		this.friends.add(friends);
	}
	
	public Set<Friends> viewFriends() throws NullPointerException{
		Set<Friends> friends = null;
		if(this.friends != null) {
			friends = this.friends;
		}
		return friends;
	}*/
}
