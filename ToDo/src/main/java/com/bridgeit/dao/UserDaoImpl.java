package com.bridgeit.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.entity.User;
import com.bridgeit.utilities.Encryption;

@Repository
public class UserDaoImpl implements UserDao {

	Logger logger = Logger.getLogger(UserDaoImpl.class);
	@Autowired
	Encryption encryption;

	@Autowired
	SessionFactory sessionFactory;
	Session session;

	public Integer registerUser(User user) {
		logger.info("Session Factory: " + sessionFactory);
		Integer id = null;
		// hibernate code here

		// open session

		session = sessionFactory.getCurrentSession();

		// store user
		if (user.getPassword() != null) {
			user.setPassword(encryption.encryptPassword(user.getPassword()));
			logger.info("Encrypted password is: " + user.getPassword() + " length= " + user.getPassword().length());

			id = (Integer) session.save(user);
			if (id == -1)
				return -1;
		}
		// session.close();
		// do not close session. spring does it for us automatically.
		// no need to beginTransaction, or commit/roll_back manually
		// let spring handle that for us

		logger.info("Register successful in DAO");
		return id;

	}

	public void activateUser(Integer id) {
		User user;
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		user = session.get(User.class, id);
		user.setIsValid(true);
		session.update(user);
		tx.commit();
	}

	public boolean loginUser(String email, String password) {
		Session session;
		session = sessionFactory.openSession();

		if (email == null || password == null) {
			logger.info("Empty Credentials");
			return false;
		}

		// authentication logic
		@SuppressWarnings("unchecked")
		List<User> userList = session.createQuery("from User").getResultList();
		password = encryption.encryptPassword(password);
		logger.info("User entered password: " + password);
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
		logger.info("reached in getUserByEmail successfully");
		Session session = sessionFactory.openSession();
		List<User> userList = new ArrayList<>();
		// jpa
		/*
		 * CriteriaBuilder builder = session.getCriteriaBuilder(); CriteriaQuery<User>
		 * criteria = builder.createQuery(User.class);
		 */
		userList = session.createQuery("from User").getResultList();
		for (User tempUser : userList) {
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
	public void resetPassword(String email, String password) { // new password
		logger.info("reached here");
		Session session = sessionFactory.openSession();
		password = encryption.encryptPassword(password);
		// deprecated
		@SuppressWarnings("deprecation")
		User user = (User) session.createCriteria(User.class).add(Restrictions.eq("email", email)).uniqueResult();
		logger.info("user old password: " + user.getPassword());
		user.setPassword(password);
		session.update(user);
		logger.info("user new password: " + user.getPassword());

	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserById(Integer id, User user) {
		logger.debug("reached in getUserByEmail DAO successfully");
		Session session = sessionFactory.openSession();
		List<User> userList = new ArrayList<>();
		// jpa
		/*
		 * CriteriaBuilder builder = session.getCriteriaBuilder(); CriteriaQuery<User>
		 * criteria = builder.createQuery(User.class);
		 */
		userList = session.createQuery("from User").getResultList();
		for (User tempUser : userList) {
			if (tempUser.getId().compareTo(id) == 0) {
				user = tempUser;
				return user;
			}
		}

		return user;
	}
}