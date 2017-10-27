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

	@GetMapping("{uid}/homepage") // userId
	public ResponseEntity<List<Note>> showNotes() {
		List<Note> noteList = new ArrayList<>();
		noteList = noteService.getNoteList(noteList);
		return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);

	}

	@PostMapping(value = "{uId}/homepage/createnote")
	public ResponseEntity<String> createNote(@RequestBody Note note, @PathVariable("uId") Integer uId) {
		try {
			noteService.createNote(uId, note);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception E) {
			E.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping(value = "{uid}/homepage/shownote/{nid}") // noteId
	public ResponseEntity<Note> showNote(@PathVariable("nid") Integer nid) {
		Note note = noteService.getNoteById(nid);
		if (note == null) {
			System.out.println("Error Loading Note");
			return new ResponseEntity<Note>(HttpStatus.NO_CONTENT);
		}
		System.out.println("Note is loaded");
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}

	@PutMapping(value = "{uid}/homepage/updatenote/{nid}")
	public ResponseEntity<Note> updateNote(@RequestBody Note updatedNote, @PathVariable("nid") Integer nid) {
		noteService.updateNote(updatedNote);

		return new ResponseEntity<Note>(updatedNote, HttpStatus.OK);
	}

	@DeleteMapping(value = "{uid}/homepage/deletenote/{nid}")
	public ResponseEntity<String> deleteNode(@PathVariable("id") Integer nid, Note note) {
		noteService.deleteNode(note);
		return new ResponseEntity<String>("Delete Success", HttpStatus.OK);
	}

}
