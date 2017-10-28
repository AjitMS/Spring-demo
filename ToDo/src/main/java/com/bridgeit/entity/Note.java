package com.bridgeit.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "notes")
public class Note {

	@Column(name = "title")
	private String title;

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer noteId;

	@Column(name = "createdon")
	private LocalDateTime createdDate;

	@Column(name = "modifiedon")
	private LocalDateTime modifiedDate;

	@Column(name = "description")
	private String description;

	@Column(name = "isarchived")
	private boolean isArchived;

	@Column(name = "intrash")
	private boolean inTrash;

	@Column(name = "ispinned")
	private boolean isPinned;

	// @JsonIgnore is required so as to prevent Jackson fasterxml databind to go
	// into loop
	// if not mentioned, program goes into infinite loop and results into stack
	// overflow

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public Note(String title, LocalDateTime createdDate, LocalDateTime modifiedDate, String description,
			boolean isArchived, boolean inTrash, boolean isPinned) {
		this.title = title;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.description = description;
		this.isArchived = isArchived;
		this.inTrash = inTrash;
		this.isPinned = isPinned;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public boolean isArchived() {
		return isArchived;
	}

	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public boolean isInTrash() {
		return inTrash;
	}

	public void setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	@Override
	public String toString() {
		return "Note [title=" + title + ", noteId=" + noteId + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", description=" + description + ", isArchived=" + isArchived + ", inTrash=" + inTrash
				+ ", isPinned=" + isPinned + "]";
	}

	public Note() {

	}
}
