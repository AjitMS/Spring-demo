package com.bridgeit.maildemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

		/*
		 * Deprecated way: 
		 * Resource r = new ClassPathResource("ApplicationContext.xml"); BeanFactory b =
		 * new XmlBeanFactory(r);
		 */

		MailMail m = (MailMail) context.getBean("mailMail");
		String sender = "ajitshikalgar786@gmail.com";// write here sender gmail id
		String receiver = "ajitshikalgar786@gmail.com";// write here receiver id
		try {
			m.sendMail(sender, receiver, "Registration Successful", "HAHAHAHAHAHAH");
		} catch (Exception E) {
			System.out.println(E);
		}
		System.out.println("success");
		context.close();
	}
}