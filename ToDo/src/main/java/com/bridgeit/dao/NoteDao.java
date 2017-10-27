package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.entity.Note;

public interface NoteDao {
	
	public void createNote(Integer uId, Note note);

	public Note getNoteById(Integer id);

	public void updateNote(Note updatedNote);

	public void deleteNode(Note note);

	public List<Note> getNoteList();

}
