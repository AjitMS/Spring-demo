package com.bridgeit.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.entity.Note;
import com.bridgeit.entity.User;

@Repository("noteDao")
public class NoteDaoImpl implements NoteDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Note getNoteById(Integer uId, Integer nId) {
		System.out.println("got id as: " + uId);
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
		System.out.println("user id is : " + updatedNote.getUser().getId());
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		updatedNote.setUser(session.get(User.class, updatedNote.getUser().getId()));
		//System.out.println("owner of note is: "+updatedNote.getUser());
		updatedNote.setModifiedDate(LocalDateTime.now());
		session.saveOrUpdate(updatedNote);
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
		System.out.println("uId is: " + uId + " and nId is: " + nId);
		Note note = session.get(Note.class, nId);
		System.out.println("note is: " + note);
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
		System.out.println("saving notes with user id :" + uId);
		Session session = sessionFactory.getCurrentSession();
		User user = new User();
		try {
			user = session.get(User.class, uId);
		} catch (Exception E) {
			System.out.println("User Id " + uId + " does not exist");
		}
		System.out.println("User is: " + user);
		note.setUser(user);
		note.setCreatedDate(LocalDateTime.now());
		session.persist(note);
		return;
	}

}
