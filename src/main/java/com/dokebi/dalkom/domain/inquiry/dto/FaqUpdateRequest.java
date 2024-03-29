package com.dokebi.dalkom.domain.inquiry.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class FaqUpdateRequest {
	@NotNull(message = "FaqUpdateRequest title NotNull 에러")
	@NotBlank(message = "FaqUpdateRequest title NotBlank 에러")
	private String title;

	@NotNull(message = "FaqUpdateRequest content NotNull 에러")
	@NotBlank(message = "FaqUpdateRequest content NotBlank 에러")
	private String content;
}
