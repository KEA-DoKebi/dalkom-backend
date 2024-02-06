package com.dokebi.dalkom.domain.chat.config;

import static java.lang.System.*;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Generated;

@Configuration
@Generated
public class ChatGptConfig {
	@Bean
	@Qualifier("openaiRestTemplate")
	public RestTemplate openaiRestTemplate() {
		Map<String, String> env = getenv();
		String gptToken = env.get("GPT_TOKEN");

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add((request, body, execution) -> {
			request.getHeaders()
				.add("Authorization", "Bearer " + gptToken);
			return execution.execute(request, body);
		});
		return restTemplate;
	}
}

