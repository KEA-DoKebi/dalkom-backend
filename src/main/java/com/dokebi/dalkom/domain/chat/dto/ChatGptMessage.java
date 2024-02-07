package com.dokebi.dalkom.domain.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChatGptMessage {

	private String role;
	private String content;

	public ChatGptMessage(@JsonProperty("role") String role, @JsonProperty("content") String content) {
		this.role = role;
		this.content = content;
	}
}