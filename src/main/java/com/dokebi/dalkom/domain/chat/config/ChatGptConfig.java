package com.dokebi.dalkom.domain.chat.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChatGptConfig {
	@Bean
	@Qualifier("openaiRestTemplate")
	public RestTemplate openaiRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add((request, body, execution) -> {
			request.getHeaders()
				.add("Authorization", "Bearer " + "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");
			return execution.execute(request, body);
		});
		return restTemplate;
	}
}

