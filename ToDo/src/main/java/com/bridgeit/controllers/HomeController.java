package com.bridgeit.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.Note;
import com.bridgeit.service.NoteService;

@RestController("{id}/homepage")
public class HomeController {

	NoteService noteService;

	@GetMapping("{id}/homepage")//userId
	public ResponseEntity<String> greetingPage(@PathVariable("id") String id) {
		System.out.println("Success");	
		return new ResponseEntity<String>("Email is Registered successfully", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/shownote/{id}")//noteId
	public ResponseEntity<Note> showNote(@PathVariable("id") String id) {
		Note note = noteService.getNoteById(id);
		if (note == null) {
			System.out.println("Error Loading Note");
			return new ResponseEntity<Note>(HttpStatus.NO_CONTENT);
		}
		System.out.println("Note is loaded");
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}

	@PutMapping(value = "/updatenote/{id}")
	public ResponseEntity<Note> updateNote(Note updatedNote, @PathVariable("id") String id) {
		noteService.updateNote(id, updatedNote);

		return new ResponseEntity<Note>(updatedNote, HttpStatus.OK);
	}

	@DeleteMapping(value = "/deletenote/{id}")
	public ResponseEntity<String> deleteNode(@PathVariable("id") String id) {
		noteService.deleteNode(id);
		return new ResponseEntity<String>("Delete Success", HttpStatus.OK);
	}

	@GetMapping(value = "/shownotes")
	public ResponseEntity<List<Note>> showNotes() {
		List<Note> noteList = new ArrayList<>();
		noteList = noteService.getNoteList(noteList);
		return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);

	}

	@PostMapping(value = "/createnote")
	public ResponseEntity<String> createNote(Note note) {
		noteService.createNote(note);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
