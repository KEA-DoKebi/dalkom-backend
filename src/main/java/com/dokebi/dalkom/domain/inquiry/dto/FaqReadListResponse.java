package com.dokebi.dalkom.domain.inquiry.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FaqReadListResponse {
	private LocalDateTime createdAt;
	private String categoryName;
	private String title;
}
