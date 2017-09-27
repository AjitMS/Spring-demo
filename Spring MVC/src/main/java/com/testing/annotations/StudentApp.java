package com.testing.annotations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class StudentApp {

	public static void main(String[] args) {

		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AnnotationsConfig.class);
		Student student = context.getBean(Student.class);
		StudentService service = context.getBean(StudentService.class);
		service.registerStudent();
		System.out.println("Student Details: " + student);
		context.close();
	}

}
 