package com.nileshchakraborty.demo.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="comment")
public class Comment {
	@Id
	@Column(name="commentId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long commentId;
	@Column(name="comment")
	private String comment;
	@Column(name="userId")
	//@OneToMany(mappedBy="user")
	private String userId;
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
