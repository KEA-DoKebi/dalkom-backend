package com.dokebi.dalkom.domain.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
	private final RestTemplate restTemplate;

	@Autowired
	public ChatGptController(@Qualifier("openaiRestTemplate") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Value("https://api.openai.com/v1/chat/completions")
	private String apiUrl;

	// @PostMapping("/review/summary")
	// public ChatGptResponse sendQuestion(@RequestParam Long productSeq, @RequestBody ChatGptRequest request) {
	// 	//ChatGptRequest request = new ChatGptRequest("gpt-3.5-turbo-1106", review);
	// 	//ChatGptRequest request = new ChatGptRequest(request);
	// 	ChatGptResponse response = restTemplate.postForObject(apiUrl, request, ChatGptResponse.class);
	//
	// 	assert response != null;
	// 	//return response.getChoices().get(0).getMessage().getContent();
	//
	// 	return chatGptService.summarizeReview(productSeq, request);
	// }

	@PostMapping("/review/summary")
	public String sendQuestion(@RequestBody ChatGptRequest request) {
		ChatGptResponse response = restTemplate.postForObject(apiUrl, request, ChatGptResponse.class);

		//적절한 예외처리
		assert response != null;
		return response.getChoices().get(0).getMessage().getContent();
	}
}