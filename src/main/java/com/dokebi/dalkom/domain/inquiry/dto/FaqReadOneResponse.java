package com.dokebi.dalkom.domain.inquiry.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FaqReadOneResponse {
	private String title;
	private LocalDateTime createdAt;
	private String name;
	private String categoryName;
	private String content;
}
