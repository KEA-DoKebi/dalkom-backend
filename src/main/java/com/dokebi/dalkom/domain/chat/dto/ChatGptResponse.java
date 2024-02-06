package com.dokebi.dalkom.domain.chat.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Generated
public class ChatGptResponse {
	private String id;
	private String object;
	private long created;
	private String model;
	private String systemFingerprint;
	private List<Choice> choices;
	private Usage usage;

	@Getter
	@Setter
	@Generated
	public static class Usage {
		@JsonProperty("prompt_tokens")
		private int promptTokens;
		@JsonProperty("completion_tokens")
		private int completionTokens;
		@JsonProperty("total_tokens")
		private int totalTokens;
	}

	@Getter
	@Setter
	@Generated
	public static class Choice {
		private int index;
		private ChatGptMessage message;
		private String logprobs;
		@JsonProperty("finish_reason")
		private String finishReason;
	}
}