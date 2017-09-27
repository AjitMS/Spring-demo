package com.testing.annotations;

import org.springframework.stereotype.Component;

@Component
public class Student {

	private String name = "Zayn Malik";
	private int id = 007;

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", id=" + id + "]";
	}

	public Student(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public Student() {

	}

}
