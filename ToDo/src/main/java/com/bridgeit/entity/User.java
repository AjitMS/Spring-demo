package com.bridgeit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.bridgeit.customAnnotation.FieldMatch;

@Entity
@Table(name = "user")
@FieldMatch(message = "Passwords do not match")
//@Component("user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;

	@NotBlank(message = "*Required")
	@Size(min = 2, max = 15)
	@NotNull(message = "*Required")
	@Pattern(regexp = "^[A-Za-z]*$", message = "Invalid Entry")
	@Column(name = "firstname")
	private String firstName;

	@NotBlank(message = "*Required")
	@Size(min = 4, max = 10)
	@NotNull(message = "*Required")
	@Pattern(regexp = "^[A-Za-z]*$", message = "Invalid Entry")
	@Column(name = "lastname", unique = true)
	private String lastName;

	@NotBlank(message = "*Required")
	@Size(min = 6, max = 30, message = "Invalid Entry")
	@NotNull(message = "*Required")
	@Email(message = "Invalid Entry")
	@Column(name = "email")
	private String email;

	@NotBlank(message = "*Required")
	@Size(min = 4, max = 6, message = "Invalid Entry")
	@NotNull(message = "*Required")
	@Column(name = "gender")
	private String gender;

	@NotBlank(message = "*Required")
	@NotNull(message = "*Required")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	// @Past(message = "Invalid Entry")
	@Column(name = "dob")
	private String dob;

	@NotBlank(message = "*Required")
	@NotNull(message = "*Required")
	@Size(min = 10, max = 10, message = "Invalid Entry")
	@Pattern(regexp = "^[0-9]*$", message = "Invalid Entry")
	@Column(name = "phone")
	private String phone;

	@NotBlank(message = "*Required")
	@NotNull(message = "*Required")
	@Size(min = 4, max = 30, message = "Short Entry")
	@Column(name = "password")
	private String password;

	@NotBlank(message = "*Required")
	@NotNull(message = "*Required")
	@Size(min = 4, max = 30, message = "Short Entry")
	@Transient
	private String confirmPassword;

	@Column(name = "isvalid")
	private boolean isValid = false;
	
	@OneToOne
	private Token token;
	
	// figured out a way to match both passwords
	// in annotations using Class level Validator

	public boolean getIsValid() {
		return isValid;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User(String id, String firstName, String lastName, String email, String gender, String dob, String phone,
			String password, String confirmPassword) {
		this.id = id;
		this.firstName = firstName;
		this.gender = gender;
		this.lastName = lastName;
		this.email = email;
		this.dob = dob;
		this.phone = phone;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public User(String firstName, String lastName, String email, String gender, String dob, String phone,
			String password, String confirmPassword) {
		this.firstName = firstName;
		this.gender = gender;
		this.lastName = lastName;
		this.email = email;
		this.dob = dob;
		this.phone = phone;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public User() {

	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", gender=" + gender + ", dob=" + dob + ", phone=" + phone + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", isValid=" + isValid + ", token=" + token + "]";
	}

}
