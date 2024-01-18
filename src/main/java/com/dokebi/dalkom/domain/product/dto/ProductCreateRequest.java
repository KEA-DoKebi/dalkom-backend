package com.dokebi.dalkom.domain.product.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// 상품 생성 requestBody
// TODO 상품 상세 이미지 추가(후순위)
public class ProductCreateRequest {

	@NotNull
	@Positive
	private Long categorySeq;

	@NotBlank
	private String name;

	@Positive
	private Integer price;

	@NotBlank
	private String info;

	@NotNull
	@Pattern(regexp = "^([YN])$")
	private String state;

	@NotBlank
	private String imageUrl;

	@NotBlank
	private String company;

	@NotEmpty
	private List<OptionAmountDTO> prdtOptionList;
}
