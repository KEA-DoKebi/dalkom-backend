package com.dokebi.dalkom.domain.notice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeUpdateRequest {

	@NotNull(message = "NoticeUpdateRequest title NotNull 에러")
	@NotBlank(message = "NoticeUpdateRequest title NotBlank 에러")
	private String title;

	@NotNull(message = "NoticeUpdateRequest content NotNull 에러")
	@NotBlank(message = "NoticeUpdateRequest content NotBlank 에러")
	private String content;

	@NotNull(message = "NoticeUpdateRequest adminSeq NotNull 에러")
	private Long adminSeq;

	@NotNull(message = "NoticeUpdateRequest state NotNull 에러")
	@Pattern(regexp = "[NY]", message = "NoticeUpdateRequest state Pattern 에러")
	private String state;
}
