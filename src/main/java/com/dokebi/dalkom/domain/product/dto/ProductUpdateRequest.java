package com.dokebi.dalkom.domain.product.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.dokebi.dalkom.domain.option.dto.OptionAmountDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
	@NotNull(message = "ProductUpdateRequest categorySeq NotNull 에러")
	@Positive(message = "ProductUpdateRequest categorySeq Positive 에러")
	private Long categorySeq;

	@NotBlank(message = "ProductUpdateRequest name NotBlank 에러")
	private String name;

	@NotNull(message = "ProductUpdateRequest price NotNull 에러")
	@Positive(message = "ProductUpdateRequest price Positive 에러")
	private Integer price;

	@NotBlank(message = "ProductUpdateRequest info NotBlank 에러")
	private String info;

	@NotBlank(message = "ProductUpdateRequest imageUrl NotBlank 에러")
	private String imageUrl;

	@NotBlank(message = "ProductUpdateRequest company NotBlank 에러")
	private String company;

	@NotNull(message = "ProductUpdateRequest state NotNull 에러")
	@Pattern(regexp = "^([YN])$", message = "ProductUpdateRequest state Pattern 에러")
	private String state;

	@NotEmpty(message = "ProductUpdateRequest stockByOptionList NotEmpty 에러")
	private List<OptionAmountDto> optionAmountList;
}
