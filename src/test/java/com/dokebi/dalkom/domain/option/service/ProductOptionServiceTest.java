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
		// Given: 옵션 코드와 설명이 설정된 OptionCodeResponse 객체 목록을 준비합니다.
		OptionCodeResponse response1 = new OptionCodeResponse("OP1", "의상 사이즈");
		OptionCodeResponse response2 = new OptionCodeResponse("OP2", "신발 사이즈");
		List<OptionCodeResponse> responseList = Arrays.asList(response1, response2);
		// productOptionRepository의 findAllOptionCode 메서드 호출 시 준비된 목록을 반환하도록 모킹합니다.
		when(productOptionRepository.findAllOptionCode()).thenReturn(responseList);

		// When: 서비스의 readOptionList 메서드를 호출합니다.
		List<OptionCodeResponse> resultList = productOptionService.readOptionList();

		// Then: 반환된 결과가 준비된 목록과 동일한지 확인합니다.
		assertEquals(responseList, resultList);
	}

	@Test
	void readProductOptionByPrdtOptionSeqTest() {
		// Given: 특정 prdtOptionSeq에 대한 ProductOption 객체를 준비하고, findProductOptionByPrdtOptionSeq 메서드 호출 시 해당 객체를 반환하도록 모킹합니다.
		Long prdtOptionSeq = 1L;
		ProductOption productOption = ProductOption.createProductOption();
		when(productOptionRepository.findProductOptionByPrdtOptionSeq(prdtOptionSeq)).thenReturn(
			Optional.of(productOption));

		// When: 서비스의 readProductOptionByPrdtOptionSeq 메서드를 호출합니다.
		ProductOption result = productOptionService.readProductOptionByPrdtOptionSeq(prdtOptionSeq);

		// Then: 반환된 결과가 준비된 ProductOption 객체와 동일한지 확인합니다.
		assertEquals(productOption, result);
	}

	@Test
	void readProductOptionByPrdtOptionSeqNotFoundTest() {
		// Given: findProductOptionByPrdtOptionSeq 메서드 호출 시 Optional.empty()를 반환하도록 모킹합니다. 이는 해당 prdtOptionSeq에 대한 ProductOption 객체가 존재하지 않음을 나타냅니다.
		Long prdtOptionSeq = 1L;
		when(productOptionRepository.findProductOptionByPrdtOptionSeq(prdtOptionSeq)).thenReturn(Optional.empty());

		// When, Then: 서비스의 readProductOptionByPrdtOptionSeq 메서드를 호출했을 때 ProductOptionNotFoundException이 발생하는지 확인합니다.
		assertThrows(ProductOptionNotFoundException.class,
			() -> productOptionService.readProductOptionByPrdtOptionSeq(prdtOptionSeq));
	}
}
