package com.dokebi.dalkom.domain.notice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCreateRequest {

	@NotNull(message = "NoticeCreateRequest title notnull 에러")
	@NotBlank(message = "NoticeCreateRequest title notblank 에러")
	private String title;

	@NotNull(message = "NoticeCreateRequest content notnull 에러")
	@NotBlank(message = "NoticeCreateRequest content notblank 에러")
	private String content;

	@NotNull(message = "NoticeCreateRequest state notnull 에러")
	@Pattern(regexp = "[NY]", message = "NoticeCreateRequest state pattern 에러")
	private String state;

	@NotNull(message = "NoticeCreateRequest state adminSeq 에러")
	private Long adminSeq;
}
