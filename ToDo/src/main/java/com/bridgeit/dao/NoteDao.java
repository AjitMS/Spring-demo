package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.entity.Note;

public interface NoteDao {
	
	public void createNote(Integer uId, Note note);

	public Note getNoteById(Integer uId, Integer nId);

	public void updateNote(Note updatedNote);

	void deleteNote(Integer uId, Integer nId);

	public List<Note> getNoteList(Integer uId);

	public void moveToTrash(Note note);

	List<Note> getTrashedNoteList(Integer uId);

}
