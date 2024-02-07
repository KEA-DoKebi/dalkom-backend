package com.dokebi.dalkom.domain.review.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class ReviewByUserResponse {

	private Long reviewSeq;
	private String content;
	private Integer rating;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String name;
	private String imageUrl;
	private String detail;
}
