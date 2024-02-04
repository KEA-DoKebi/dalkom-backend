package com.dokebi.dalkom.domain.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dokebi.dalkom.domain.chat.dto.ChatGptRequest;
import com.dokebi.dalkom.domain.chat.dto.ChatGptResponse;

@RestController
@RequestMapping("/api/v1/chatGpt")
public class ChatGptController {
	private static final String API_URL = "https://api.openai.com/v1/chat/completions";
	private final RestTemplate restTemplate;

	@Autowired
	public ChatGptController(@Qualifier("openaiRestTemplate") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@PostMapping("/review/summary")
	public String sendQuestion(@RequestBody ChatGptRequest request) {
		ChatGptResponse response = restTemplate.postForObject(API_URL, request, ChatGptResponse.class);

		//적절한 예외처리
		assert response != null;
		return response.getChoices().get(0).getMessage().getContent();
	}
}