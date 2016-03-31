package com.david.dto;

import org.springframework.beans.factory.InitializingBean;

public class User implements InitializingBean {
	private int id;
	private String name;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println(System.currentTimeMillis());
	}

}
