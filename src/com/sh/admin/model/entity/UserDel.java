package com.sh.admin.model.entity;

import java.sql.Timestamp;

public class UserDel {
	private int no;
	private String userId;
	private String userName;
	private Timestamp createdAt;
	private Timestamp delAt;
	
	public UserDel() {}
	
	public UserDel(int no, String userId, String userName, Timestamp createdAt, Timestamp delAt) {
		this.no = no;
		this.userId = userId;
		this.userName = userName;
		this.createdAt = createdAt;
		this.delAt = delAt;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getDelAt() {
		return delAt;
	}
	public void setDelAt(Timestamp delAt) {
		this.delAt = delAt;
	}
	
	@Override
	public String toString() {
		return "UserLogDel [no=" + no + ", userId=" + userId + ", userName=" + userName + ", createdAt=" + createdAt
				+ ", delAt=" + delAt + "]";
	}
}
