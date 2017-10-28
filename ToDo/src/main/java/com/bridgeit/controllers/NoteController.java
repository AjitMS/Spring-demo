package com.bridgeit.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.Note;
import com.bridgeit.service.NoteService;
import com.bridgeit.service.UserService;

@RestController("{uid}/homepage")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;

	@GetMapping("{uId}/homepage") // userId
	public ResponseEntity<List<Note>> showNotes(@PathVariable("uId") Integer uId) {
		List<Note> noteList = new ArrayList<>();
		noteList = noteService.getNoteList(uId);
		if (noteList.size() == 0) {
			System.out.println("No notes found for user ID: " + uId);
			return new ResponseEntity<List<Note>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);

	}

	@PostMapping(value = "{uId}/homepage/createnote")
	public ResponseEntity<String> createNote(@RequestBody Note note, @PathVariable("uId") Integer uId) {
		System.out.println("Id is: " + uId);
		try {
			noteService.createNote(uId, note);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception E) {
			E.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping(value = "{uId}/homepage/shownote/{nId}") // noteId
	public ResponseEntity<Note> showNote(@PathVariable("uId") Integer uId, @PathVariable("nId") Integer nId) {
		Note note;
		try {
			note = noteService.getNoteById(uId, nId);
		} catch (Exception E) {
			System.out.println("Error Loading Note / Note not found");
			return new ResponseEntity<Note>(HttpStatus.NO_CONTENT);
		}

		System.out.println("Note is loaded");
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}

	@PutMapping(value = "{uid}/homepage/updatenote/{nid}")
	public ResponseEntity<Note> updateNote(@RequestBody Note updatedNote, @PathVariable("nid") Integer nid) {
		System.out.println("Here i stand");
		noteService.updateNote(updatedNote);

		return new ResponseEntity<Note>(updatedNote, HttpStatus.OK);
	}

	@DeleteMapping(value = "{uId}/homepage/deletenote/{nId}")
	public ResponseEntity<String> deleteNode(@PathVariable("nId") Integer nId,
			@PathVariable("uId") Integer uId) {
		noteService.deleteNote(uId, nId);
		return new ResponseEntity<String>("CHECK DB", HttpStatus.OK);
	}

}
