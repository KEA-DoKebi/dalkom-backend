package com.dokebi.dalkom.domain.option.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// 옵션코드와 이름을 리스트의 형태로 같이 반환하기 위한 DTO
public class OptionCodeResponse {
	private String optionCode;
	private String name;
}
