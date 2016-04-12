package com.david.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class FastUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JSONField(name = "user_id")
	private Integer userId;

	@JSONField(name = "user_name")
	private String userName;

	public FastUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FastUser(Integer userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "FastUser [userId=" + userId + ", userName=" + userName + "]";
	}

}
