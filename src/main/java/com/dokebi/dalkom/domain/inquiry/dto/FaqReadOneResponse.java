package com.dokebi.dalkom.domain.inquiry.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FaqReadOneResponse {
	private String title;
	private LocalDateTime createdAt;
	private String name;
	private String categoryName;
	private String content;
}
