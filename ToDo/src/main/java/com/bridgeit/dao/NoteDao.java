package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.entity.Note;

public interface NoteDao {
	public Note getNoteById(String id);

	public void updateNote(String id, Note updatedNote);

	public void deleteNode(String id);

	public List<Note> getNoteList();

	public void createNote(Note note);

}
