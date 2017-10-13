package com.bridgeit.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

	public boolean loginUser(String email, String password) {
		Session session;
		session = (Session) sessionFactory.openSession();

		if (email == null || password == null) {
			System.out.println("Empty Credentials");
			return false;
		}

		// authentication logic
		@SuppressWarnings("unchecked")
		List<User> userList = session.createQuery("from User").getResultList();

		for (User tempUser : userList)
			if (tempUser.getEmail().equals(email)) {
				if (tempUser.getPassword().equals(password)) {
					return true;
				}
			}
		return false;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public User getUserByEmail(String email, User user) {
		System.out.println("reached in getUserByEmail successfully");
		Session session = sessionFactory.openSession();
		List<User> userList = new ArrayList<>();
		// jpa
		/*
		 * CriteriaBuilder builder = session.getCriteriaBuilder(); CriteriaQuery<User>
		 * criteria = builder.createQuery(User.class);
		 */
		userList = session.createQuery("from User").getResultList();
		for (User tempUser : userList) {
			System.out.println(tempUser.getEmail() + " vs " + email);
			if (tempUser.getEmail().equalsIgnoreCase(email)) {
				user = tempUser;
				return user;
			}
		}

		return user;
	}

	List<User> userList;

	@Override
	public boolean userExists(User user) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<User> userList = session.createCriteria(User.class).add(Restrictions.eq("email", user.getEmail())).list();
		if (userList.size() == 0) {
			return false;
		}

		return true;
	}

	@Override
	public void resetPassword(String email, String password) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<User> userList = session.createCriteria(User.class).add(Restrictions.eq("email", email)).list();
		for (User tempUser : userList) {
			tempUser.setPassword(password);
			tempUser.setConfirmPassword(password);
		}
		return;
	}
}