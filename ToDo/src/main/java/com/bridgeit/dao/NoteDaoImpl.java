package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.entity.Note;

public class NoteDaoImpl implements NoteDao {

	@Override
	public Note getNoteById(String id) {
		Note note = null;
		// get note from database
		return note;
	}

	@Override
	public void updateNote(String id, Note updatedNote) {

		// update node in database
	}

	@Override
	public void deleteNode(String id) {
		// delete node from database

	}

	@Override
	public List<Note> getNoteList() {
		// bring entire note list from database
		return null;
	}

	@Override
	public void createNote(Note note) {
		// create a new node and save into database

	}

}
