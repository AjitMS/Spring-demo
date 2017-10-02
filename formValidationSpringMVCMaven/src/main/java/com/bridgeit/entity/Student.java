package com.bridgeit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	@Size(min = 3, max = 20)
	@NotBlank
	private String firstName;

	@Size(min = 3, max = 20)
	@NotBlank
	private String lastName;

	@NotNull
	private String sex;

	@NotNull
	@Size(min = 3, max = 25)
	@Email
	@NotNull
	private String email;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Past
	private Date dob;

	@NotNull
	private String section;

	@NotNull
	private String country;

	private boolean firstAttempt;

	@NotNull
	private List<String> subjects = new ArrayList<String>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isFirstAttempt() {
		return firstAttempt;
	}

	public void setFirstAttempt(boolean firstAttempt) {
		this.firstAttempt = firstAttempt;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Student [firstName=" + firstName + ", lastName=" + lastName + ", sex=" + sex + ", email=" + email
				+ ", dob=" + dob + ", section=" + section + ", country=" + country + ", firstAttempt=" + firstAttempt
				+ ", subjects=" + subjects + "]";
	}

}
