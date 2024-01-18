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

	@NotNull
	@NotBlank
	private String name;

	@NotNull
	@Positive
	private Integer price;

	@NotNull
	@NotBlank
	private String info;

	@NotNull
	@NotBlank
	@Pattern(regexp = "^([YN])$")
	private String state;

	@NotNull
	@NotBlank
	private String imageUrl;

	@NotNull
	@NotBlank
	private String company;

	@NotNull
	@NotEmpty
	private List<OptionAmountDTO> prdtOptionList;
}
