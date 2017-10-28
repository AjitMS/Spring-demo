package com.bridgeit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.NoteDao;
import com.bridgeit.entity.Note;

@Service("noteService")
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteDao dao;

	@Override
	@Transactional
	public Note getNoteById(Integer uId, Integer nId) {
		Note note = dao.getNoteById(uId, nId);
		return note;
	}

	@Override
	@Transactional
	public void updateNote(Note updatedNote) {
		dao.updateNote(updatedNote);
		return;
	}

	@Override
	@Transactional
	public void deleteNote(Integer uId, Integer nId) {
		dao.deleteNote(uId, nId);
	}

	@Override
	public List<Note> getNoteList(Integer uId) {
		List<Note> noteList;
		noteList = dao.getNoteList(uId);
		return noteList;
	}

	@Override
	@Transactional
	public void createNote(Integer uId, Note note) {
		dao.createNote(uId, note);
		return;

	}

	@Override
	@Transactional
	public void moveToTrash(Note note) {
		dao.moveToTrash(note);
	}

	@Override	
	@Transactional
	public List<Note> getTrashedNoteList(Integer uId) {
		List<Note> trashedNoteList = dao.getTrashedNoteList(uId);
		if (trashedNoteList.size() == 0)
			return null;
		return trashedNoteList;
	}

}
