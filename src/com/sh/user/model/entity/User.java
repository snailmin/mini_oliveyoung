package com.sh.user.model.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
	private String id;
	private String name;
	private String password;
	private Date birthday;
	private String skinType;
	private Timestamp createdAt;
	private double mileage;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String id, String name, String password, Date birthday, String skinType, Timestamp createdAt,
			double mileage) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
		this.skinType = skinType;
		this.createdAt = createdAt;
		this.mileage = mileage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSkinType() {
		return skinType;
	}
	public void setSkinType(String skinType) {
		this.skinType = skinType;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", birthday=" + birthday + ", skinType="
				+ skinType + ", createdAt=" + createdAt + ", mileage=" + mileage + "]";
	}
	
	
	
}
