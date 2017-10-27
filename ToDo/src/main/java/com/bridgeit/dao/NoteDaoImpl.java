package com.bridgeit.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.entity.Note;
import com.bridgeit.entity.User;

@Repository("noteDao")
public class NoteDaoImpl implements NoteDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Note getNoteById(Integer id) {
		Note note = new Note();
		// get note from database
		Session session = sessionFactory.openSession();
		note = (Note) session.get(Note.class, id);
		return note;
	}

	@Override
	public void updateNote(Note updatedNote) {
		Session session = sessionFactory.openSession();
		// update node in database
		session.saveOrUpdate(updatedNote);
		return;
	}

	@Override
	public void deleteNode(Note note) {
		Session session = sessionFactory.openSession();
		// delete node from database
		session.delete(note);
		return;
	}

	@Override
	public List<Note> getNoteList() {
		// bring entire note list from database
		Session session = sessionFactory.openSession();

		// since session.createCriteria() is deprecated

		// Create CriteriaBuilder
		CriteriaBuilder builder = session.getCriteriaBuilder();

		// Create CriteriaQuery
		CriteriaQuery<Note> criteria = builder.createQuery(Note.class);
		criteria.from(Note.class);
		List<Note> noteList = session.createQuery(criteria).getResultList();

		return noteList;
	}

	@Override
	public void createNote(Integer uId, Note note) {
		System.out.println("saving note");
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, uId);
		note.setUser(user);
		session.save(note);
		return;
	}

}
