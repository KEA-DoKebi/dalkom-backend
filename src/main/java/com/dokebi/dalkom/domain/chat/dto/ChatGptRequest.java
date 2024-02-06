package com.dokebi.dalkom.domain.chat.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Generated
public class ChatGptRequest {
	private String model;
	@JsonProperty("max_tokens")
	private Integer maxTokens;
	private Double temperature;
	//private Boolean stream;
	private List<ChatGptMessage> messages = new ArrayList<>();

	@Builder
	public ChatGptRequest(List<ChatGptMessage> messages) {
		//this.messages = new ArrayList<>();
		this.model = "gpt-3.5-turbo-1106";
		this.maxTokens = 1000;
		this.temperature = 0.0;
		this.messages.add(new ChatGptMessage("system",
			"You are review summary system for Korean shopping malls. Analyze the provided review(s) and summarize in third person. Never use subjects in sentences. Responses should be in Korean and within 100 tokens."));
		this.messages.addAll(messages);
	}
}