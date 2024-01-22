package com.dokebi.dalkom.domain.option.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OptionCodeResponseTest {

	OptionCodeResponse optionCodeResponse;

	@BeforeEach
	void overallTest() {
		// given
		optionCodeResponse = new OptionCodeResponse("OP1", "의상 사이즈");
	}

	@Test
	void getOptionCode() {
		// when
		String optionCode = optionCodeResponse.getOptionCode();

		//then
		Assertions.assertEquals("OP1", optionCode);
	}

	@Test
	void getName() {
		// when
		String name = optionCodeResponse.getName();

		//then
		Assertions.assertEquals("의상 사이즈", name);
	}

	@Test
	void setOptionCode() {
		// when
		optionCodeResponse.setOptionCode("OP2");

		//then
		Assertions.assertEquals("OP2", optionCodeResponse.getOptionCode());
	}

	@Test
	void setName() {
		// when
		optionCodeResponse.setName("신발 사이즈");

		//then
		Assertions.assertEquals("신발 사이즈", optionCodeResponse.getName());
	}
}