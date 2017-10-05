package com.bridgeit.service;

import java.util.List;

import com.bridgeit.entity.Note;

public interface NoteService {

	public Note getNoteById(String id);

	public void updateNote(String id, Note updatedNote);

	public void deleteNode(String id);

	public void createNote(Note note);

	List<Note> getNoteList(List<Note> noteList);

}
