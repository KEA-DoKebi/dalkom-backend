package com.dokebi.dalkom.domain.review.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class ReviewByProductResponse {

	private String nickname;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Integer rating;

}
