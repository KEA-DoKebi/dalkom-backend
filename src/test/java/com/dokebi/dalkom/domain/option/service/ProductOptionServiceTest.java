package com.dokebi.dalkom.domain.option.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.option.dto.OptionCodeResponse;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.exception.ProductOptionNotFoundException;
import com.dokebi.dalkom.domain.option.repository.ProductOptionRepository;

@ExtendWith(MockitoExtension.class)
public class ProductOptionServiceTest {

	@InjectMocks
	private ProductOptionService productOptionService;

	@Mock
	private ProductOptionRepository productOptionRepository;

	@Test
	void readOptionListTest() {
		// Given
		OptionCodeResponse response1 = new OptionCodeResponse("OP1", "의상 사이즈");
		OptionCodeResponse response2 = new OptionCodeResponse("OP2", "신발 사이즈");
		List<OptionCodeResponse> responseList = Arrays.asList(response1, response2);
		when(productOptionRepository.findAllOptionCode()).thenReturn(responseList);

		// When
		List<OptionCodeResponse> resultList = productOptionService.readOptionList();

		// Then
		assertEquals(responseList, resultList);
	}

	// @Test
	// void readOptionDetailListTest() {
	// 	// Given
	// 	String optionCode = "OP1";
	// 	List<String> details = Arrays.asList("S", "M", "L", "XL", "XXL");
	// 	when(productOptionRepository.findDetailByOptionCode(optionCode)).thenReturn(details);
	//
	// 	// When
	// 	List<OptionDetailListResponse> resultList = productOptionService.readOptionDetailList(optionCode);
	//
	// 	// Then
	// 	assertEquals(details, resultList);
	// }

	@Test
	void readProductOptionByPrdtOptionSeqTest() {
		// Given
		Long prdtOptionSeq = 1L;
		ProductOption productOption = ProductOption.createProductOption();
		when(productOptionRepository.findProductOptionByPrdtOptionSeq(prdtOptionSeq)).thenReturn(
			Optional.of(productOption));

		// When
		ProductOption result = productOptionService.readProductOptionByPrdtOptionSeq(prdtOptionSeq);

		// Then
		assertEquals(productOption, result);
	}

	@Test
	void readProductOptionByPrdtOptionSeqNotFoundTest() {
		// Given
		Long prdtOptionSeq = 1L;
		when(productOptionRepository.findProductOptionByPrdtOptionSeq(prdtOptionSeq)).thenReturn(Optional.empty());

		// When, Then
		assertThrows(ProductOptionNotFoundException.class,
			() -> productOptionService.readProductOptionByPrdtOptionSeq(prdtOptionSeq));

	}
}
