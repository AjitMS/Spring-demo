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
	public Note getNoteById(Integer id) {
		Note note = dao.getNoteById(id);
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
	public void deleteNode(Note note) {
		dao.deleteNode(note);
	}

	@Override
	@Transactional
	public List<Note> getNoteList(List<Note> noteList) {
		noteList = dao.getNoteList();
		return noteList;
	}

	@Override
	@Transactional
	public void createNote(Integer uId, Note note) {
		dao.createNote(uId, note);
		return;

	}

}
