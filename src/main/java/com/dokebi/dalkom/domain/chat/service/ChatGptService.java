package com.dokebi.dalkom.domain.chat.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.dokebi.dalkom.domain.chat.dto.ChatGptMessage;
import com.dokebi.dalkom.domain.chat.dto.ChatGptRequest;
import com.dokebi.dalkom.domain.chat.dto.ChatGptResponse;
import com.dokebi.dalkom.domain.chat.exception.GptResponseFailException;
import com.dokebi.dalkom.domain.review.dto.ReviewSimpleDto;

@Service
public class ChatGptService {

	private static final String API_URL = "https://api.openai.com/v1/chat/completions";
	private final RestTemplate restTemplate;

	@Autowired
	public ChatGptService(@Qualifier("openaiRestTemplate") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String processChatGptRequest(List<ReviewSimpleDto> reviewList) {
		try {
			List<ChatGptMessage> messages = reviewList.stream()
				.map(review -> new ChatGptMessage("user", review.getContent()))
				.collect(Collectors.toList());

			ChatGptRequest chatGptRequest = new ChatGptRequest(messages);

			ChatGptResponse response = restTemplate.postForObject(API_URL, chatGptRequest, ChatGptResponse.class);

			if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
				throw new GptResponseFailException("빈 응답이 돌아왔습니다.");
			}

			return response.getChoices().get(0).getMessage().getContent();

		} catch (RestClientException e) {
			throw new GptResponseFailException(e.getMessage());
		}
	}
}
