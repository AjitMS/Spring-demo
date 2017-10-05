package com.bridgeit.dao;

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

		// hibernate code here

		// open session

		try {
			session = sessionFactory.getCurrentSession();

			// store user
			session.save(user);

		} catch (Exception E) {
			System.out.println(E);
		}

		finally {
			session.close();
		}
		// no need to beginTransaction, or commit/roll_back manually
		// let spring handle that for us

		return;

	}

	public boolean loginUser(User user) {
		Session session;
		session = (Session) sessionFactory.openSession();

		// authentication logic
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<User> userList = session.createCriteria(User.class).list();
		for (User tempUser : userList)
			if (tempUser.getId().equals(user.getId()))
				if (tempUser.getPassword().equals(user.getPassword())) {
					System.out.println("Logged In");
					return true;
				}
		return false;
	}

}
