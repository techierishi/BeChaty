package com.rishi.bechaty.entity;

public class ChatEntity {

	private String username;
	private String message_body;
	private boolean out;

	
	public boolean isOut() {
		return out;
	}

	public void setOut(boolean out) {
		this.out = out;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage_body() {
		return message_body;
	}

	public void setMessage_body(String message_body) {
		this.message_body = message_body;
	}

}
