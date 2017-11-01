package com.bridgeit.emailUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailCredentialSerializer {
	public static final String credentialFilePath = "/home/bridgelabz3/ajit/AjitMiscFIles/myEmail.dat";

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		Logger logger = LoggerFactory.getLogger(EmailCredentialSerializer.class);
		String workingDirectory = System.getProperty("user.dir");
		logger.info("*****Working directory is " + workingDirectory);
		serializeCredentials("email", "password");
		EmailInfo emailInfo = getEmailInfo();
		logger.info("Email Info is {}", emailInfo);
	}

	public static void serializeCredentials(String email, String password) throws FileNotFoundException, IOException {
		EmailInfo emailInfo = new EmailInfo(email, password);
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(credentialFilePath))) {
			output.writeObject(emailInfo);
			 output.close();

		}
	}

	public static EmailInfo getEmailInfo() throws FileNotFoundException, IOException, ClassNotFoundException {

		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(credentialFilePath))) {
			System.out.println("Inside getEmailInfo() method");
			EmailInfo emailInfo = (EmailInfo) input.readObject();
			return emailInfo;
		}
	}
}
