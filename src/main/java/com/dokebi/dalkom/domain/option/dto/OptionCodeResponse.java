package com.dokebi.dalkom.domain.option.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionCodeResponse {
	private String optionCode;
	private String name;
}
