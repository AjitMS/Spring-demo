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
		System.out.println("Session Factory: "+sessionFactory);
		// hibernate code here

		// open session

			session = sessionFactory.getCurrentSession();
			// store user	
			session.save(user);	
			//session.close();
			//do not close session. spring does it for us automatically.
		// no need to beginTransaction, or commit/roll_back manually
		// let spring handle that for us
		System.out.println("Register successful in DAO");
		return;

	}

	public boolean loginUser(User user) {
		Session session;
		session = (Session) sessionFactory.openSession();

		// authentication logic
		@SuppressWarnings("unchecked")
		List<User> userList = session.createQuery("from Student").getResultList();
		for (User tempUser : userList)
			if (tempUser.getId().equals(user.getId()))
				if (tempUser.getPassword().equals(user.getPassword())) {
					System.out.println("Logged In");
					return true;
				}
		return false;
	}

}
