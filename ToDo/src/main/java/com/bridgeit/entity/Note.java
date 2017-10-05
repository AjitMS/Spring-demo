package com.bridgeit.entity;

public class Note {

	private String headerText;
	private String id;
	private String bodyText;

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	@Override
	public String toString() {
		return "Note [headerText=" + headerText + ", id=" + id + ", bodyText=" + bodyText + "]";
	}

	public Note(String headerText, String id, String bodyText) {
		this.headerText = headerText;
		this.id = id;
		this.bodyText = bodyText;
	}

	public Note() {

	}
}
