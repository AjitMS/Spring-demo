package com.bridgeit.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.entity.Note;

public class NoteServiceImpl implements NoteService {

	@Override
	@Transactional
	public Note getNoteById(String id) {
		Note note = new Note();

		return note;
	}

	@Override
	@Transactional
	public void updateNote(String id, Note updatedNote) {

	}

	@Override
	@Transactional
	public void deleteNode(String id) {

	}

	@Override
	@Transactional
	public List<Note> getNoteList(List<Note> noteList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void createNote(Note note) {
		// TODO Auto-generated method stub

	}

}
