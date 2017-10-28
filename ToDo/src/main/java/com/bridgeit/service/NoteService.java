package com.bridgeit.service;

import java.util.List;

import com.bridgeit.entity.Note;

public interface NoteService {

	public Note getNoteById(Integer uId, Integer nId);

	public void updateNote(Note updatedNote);

	public void createNote(Integer uId, Note note);

	List<Note> getNoteList(Integer uId);

	void deleteNote(Integer uId, Integer nId);

	public void moveToTrash(Note note);

	List<Note> getTrashedNoteList(Integer uId);

}
