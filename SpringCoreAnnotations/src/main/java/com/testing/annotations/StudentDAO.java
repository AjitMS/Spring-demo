package com.testing.annotations;

import org.springframework.stereotype.Repository;

@Repository("dao")
public class StudentDAO implements StudentDAOInterface {

	private String status = "done";

	@Override
	public String toString() {
		return "StudentDAO [status=" + status + "]";
	}

	public StudentDAO() {

	}

	@Override
	public void addtoDatabase() {
		System.out.println("Student added to DataBase through DAO " + status);
	}

}
