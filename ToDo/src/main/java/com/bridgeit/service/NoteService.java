package com.bridgeit.service;

import java.util.List;

import com.bridgeit.entity.Note;

public interface NoteService {

	public Note getNoteById(Integer id);

	public void updateNote(Note updatedNote);

	public void deleteNode(Note note);

	public void createNote(Integer uId, Note note);

	List<Note> getNoteList(List<Note> noteList);

}
