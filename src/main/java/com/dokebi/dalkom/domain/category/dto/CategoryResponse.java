package com.dokebi.dalkom.domain.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponse {

	private Long categorySeq;
	private String name;
	private String imageUrl;
}
