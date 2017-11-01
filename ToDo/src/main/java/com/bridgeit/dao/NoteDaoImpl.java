package com.bridgeit.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.entity.Note;
import com.bridgeit.entity.User;

@Repository("noteDao")
public class NoteDaoImpl implements NoteDao {
	Logger logger = Logger.getLogger(NoteDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Note getNoteById(Integer uId, Integer nId) {
		logger.info("got id in DAO as: " + uId);
		Note note = new Note();
		// get note from database
		Session session = sessionFactory.openSession();

		note = (Note) session.get(Note.class, nId);
		// to verify note belongs to same user
		if (note.getUser().getId().compareTo(uId) == 0)
			return note;
		return null;
	}

	@Override
	public void updateNote(Note updatedNote) {
		logger.info("user id is : " + updatedNote.getUser().getId());
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		updatedNote.setModifiedDate(LocalDateTime.now());
		session.update(updatedNote);
		tx.commit();

	}

	@Override
	public void moveToTrash(Note note) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		note = session.get(Note.class, note.getNoteId());
		note.setInTrash(true);
		tx.commit();
		return;
	}

	@Override
	public void pinNote(Note note) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		note = session.get(Note.class, note.getNoteId());
		note.setPinned(true);
		tx.commit();
		return;
	}

	@Override
	public void archiveNote(Note note) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		note = session.get(Note.class, note.getNoteId());
		note.setArchived(true);
		tx.commit();
		return;
	}

	@Override
	public void deleteNote(Integer uId, Integer nId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		logger.info("uId is: " + uId + " and nId is: " + nId);
		Note note = session.get(Note.class, nId);
		logger.info("note is: " + note);
		session.delete(note);
		tx.commit();
		session.close();
		return;
	}

	@Override
	public List<Note> getNoteList(Integer uId) {
		// bring entire note list from database
		Session session = sessionFactory.openSession();

		// since session.createCriteria() is deprecated

		// Create CriteriaBuilder
		CriteriaBuilder builder = session.getCriteriaBuilder();

		// Create CriteriaQuery
		CriteriaQuery<Note> criteria = builder.createQuery(Note.class);

		criteria.from(Note.class);
		List<Note> entireNoteList = session.createQuery(criteria).getResultList();

		// learn a more efficient way to retrieve notes

		List<Note> noteList = new ArrayList<>();
		for (Note tempNote : entireNoteList)
			// if note is temporarily deleted
			if (tempNote.getUser().getId().compareTo(uId) == 0 && !tempNote.isInTrash())
				noteList.add(tempNote);

		return noteList;
	}

	@Override
	public List<Note> getTrashedNoteList(Integer uId) {
		// bring entire note list from database
		Session session = sessionFactory.openSession();

		// since session.createCriteria() is deprecated

		// Create CriteriaBuilder
		CriteriaBuilder builder = session.getCriteriaBuilder();

		// Create CriteriaQuery
		CriteriaQuery<Note> criteria = builder.createQuery(Note.class);

		criteria.from(Note.class);
		List<Note> entireNoteList = session.createQuery(criteria).getResultList();

		// learn a more efficient way to retrieve notes

		List<Note> noteList = new ArrayList<>();
		for (Note tempNote : entireNoteList)
			// if note is temporarily deleted
			if (tempNote.getUser().getId().compareTo(uId) == 0 && tempNote.isInTrash())
				noteList.add(tempNote);

		return noteList;
	}

	@Override
	public void createNote(Integer uId, Note note) {
		logger.info("saving notes with user id :" + uId);
		Session session = sessionFactory.getCurrentSession();
		User user = new User();
		try {

			user = session.get(User.class, uId);
			note.setUser(user);
			note.setCreatedDate(LocalDateTime.now());

		} catch (Exception E) {
			logger.info("User Id " + uId + " does not exist");
			return;
		}
		logger.info("User is: " + user);
		session.persist(note);
		return;
	}

}
