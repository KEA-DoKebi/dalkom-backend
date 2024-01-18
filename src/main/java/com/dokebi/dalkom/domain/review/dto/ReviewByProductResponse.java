package com.dokebi.dalkom.domain.review.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewByProductResponse {

	private String nickname;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Integer rating;

}
