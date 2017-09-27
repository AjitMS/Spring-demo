package com.sample.project;

import org.springframework.stereotype.Component;

@Component("license")
public class License {

	private int id = 2;
	private String type = "Two-wheeler";

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "License [id=" + id + ", type=" + type + "]";
	}

	public License(int id, String type) {
		this.id = id;
		this.type = type;
	}

	public License() {
		// TODO Auto-generated constructor stub
	}

}
