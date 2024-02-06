package com.dokebi.dalkom.domain.inquiry.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FaqReadOneResponseTest {
	@Test
	@DisplayName("Getter 및 Setter 테스트")
	void FaqReadOneResponseGetterSetterTest() {
		// Given
		String title = "Title";
		LocalDateTime time = LocalDateTime.now();
		String name = "홍길동";
		String categoryName = "CategoryName";
		String content = "Content";

		// When
		FaqReadOneResponse response = new FaqReadOneResponse();
		response.setTitle(title);
		response.setCreatedAt(time);
		response.setName(name);
		response.setCategoryName(categoryName);
		response.setContent(content);

		// Then
		assertNotNull(response);
		assertEquals(title, response.getTitle());
		assertEquals(time, response.getCreatedAt());
		assertEquals(categoryName, response.getCategoryName());
		assertEquals(content, response.getContent());
	}
}
