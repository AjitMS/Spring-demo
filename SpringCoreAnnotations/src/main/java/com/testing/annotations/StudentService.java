package com.testing.annotations;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("service")

public class StudentService {

	@Resource(name="dao")
	private StudentDAO dao;

	public StudentService() {
		System.out.println("Value of dao is: "+dao);
	}

	public StudentService(StudentDAO dao) {
		this.dao = dao;
	}

	public StudentDAO getDao() {
		return dao;
	}

	public void setDao(StudentDAO dao) {
		this.dao = dao;
	}

	public void registerStudent() {

		System.out.println("Data is in Service");
		dao.addtoDatabase();
	}

	@Override
	public String toString() {
		return "StudentService [dao=" + dao + "]";
	}
}
