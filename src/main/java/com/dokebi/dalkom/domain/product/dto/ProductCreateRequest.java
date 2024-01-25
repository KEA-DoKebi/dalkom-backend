package com.dokebi.dalkom.domain.product.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.dokebi.dalkom.domain.option.dto.OptionAmountDto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
// 상품 생성 requestBody
// TODO 상품 상세 이미지 추가(후순위)
public class ProductCreateRequest {

	@NotNull(message = "ProductCreateRequest categorySeq NotNull 에러")
	@Positive(message = "ProductCreateRequest categorySeq Positive 에러")
	private Long categorySeq;

	@NotBlank(message = "ProductCreateRequest name NotBlank 에러")
	private String name;

	@NotNull(message = "ProductCreateRequest price NotNull 에러")
	@Positive(message = "ProductCreateRequest price Positive 에러")
	private Integer price;

	@NotBlank(message = "ProductCreateRequest info NotBlank 에러")
	private String info;

	@NotNull(message = "ProductCreateRequest categorySeq NotNull 에러")
	@Pattern(regexp = "^([YN])$", message = "ProductCreateRequest state Pattern 에러")
	private String state;

	@NotBlank(message = "ProductCreateRequest imageUrl NotBlank 에러")
	private String imageUrl;

	@NotBlank(message = "ProductCreateRequest company NotBlank 에러")
	private String company;

	@NotEmpty(message = "ProductCreateRequest prdtOptionList NotEmpty 에러")
	private List<OptionAmountDto> prdtOptionList;
}
