package com.dokebi.dalkom.domain.option.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.option.dto.OptionCodeResponse;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.exception.ProductOptionNotFoundException;
import com.dokebi.dalkom.domain.option.repository.ProductOptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOptionService {
	private final ProductOptionRepository productOptionRepository;

	public List<OptionCodeResponse> readOptionList() {
		return productOptionRepository.findAllOptionCode();
	}

	public List<String> readOptionDetailList(String optionCode) {
		return productOptionRepository.findDetailByOptionCode(optionCode);
	}

	public String readOptionDetailByPdtOptionSeq(Long prdtOptionSeq) {
		return productOptionRepository.findDetailByPrdtOptionSeq(prdtOptionSeq);
	}

	public ProductOption readProductOptionByPrdtOptionSeq(Long prdtOptionSeq) {
		return productOptionRepository.findProductOptionByPrdtOptionSeq(prdtOptionSeq)
			.orElseThrow(ProductOptionNotFoundException::new);
	}
}
