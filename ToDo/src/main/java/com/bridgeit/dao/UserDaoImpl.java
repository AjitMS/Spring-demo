package com.bridgeit.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory sessionFactory;
	Session session;

	public void registerUser(User user) {
		System.out.println("Session Factory: " + sessionFactory);
		// hibernate code here

		// open session

		session = sessionFactory.getCurrentSession();
		// store user
		session.save(user);
		// session.close();
		// do not close session. spring does it for us automatically.
		// no need to beginTransaction, or commit/roll_back manually
		// let spring handle that for us
		System.out.println("Register successful in DAO");
		return;

	}

	public void validateRegisteredUser(String id) {
		User user;
		Session session = sessionFactory.openSession();
		user = session.get(User.class, id);
		System.out.println("isvalid initially: " + user.getIsValid());
		user.setIsValid(true);
		session.saveOrUpdate(user);
		System.out.println("isvalid finally: " + user.getIsValid());
	}

	public boolean loginUser(User user) {
		Session session;
		session = (Session) sessionFactory.openSession();

		if (user == null) {
			System.out.println("Empty Credentials");
			return false;
		}

		// authentication logic
		@SuppressWarnings("unchecked")
		List<User> userList = session.createQuery("from User").getResultList();

		for (User tempUser : userList)
			if (tempUser.getEmail().equals(user.getEmail())) {
				System.out.println("db email: " + tempUser.getEmail() + " user email: " + user.getEmail());
				if (tempUser.getPassword().equals(user.getPassword())) {
					System.out.println("db pass: " + tempUser.getPassword() + " user pass: " + user.getPassword());
					return true;
				}
			}
		return false;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public User getUserByEmail(String email, User user) {
		Session session = sessionFactory.openSession();
		List<User> userList = new ArrayList<>();
		userList = session.createQuery("from User").getResultList();

		for (User tempUser : userList)
			if (tempUser.getEmail().equalsIgnoreCase(email)) {
				user = tempUser;
				return user;
			}

		return user;
	}

}
