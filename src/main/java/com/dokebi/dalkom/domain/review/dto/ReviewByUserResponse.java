package com.dokebi.dalkom.domain.review.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewByUserResponse {

	private String content;
	private Integer rating;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String name;
	private String imageUrl;
	private String detail;

}
