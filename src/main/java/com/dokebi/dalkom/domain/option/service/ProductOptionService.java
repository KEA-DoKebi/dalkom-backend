package com.dokebi.dalkom.domain.option.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.option.dto.OptionCodeResponse;
import com.dokebi.dalkom.domain.option.repository.ProductOptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductOptionService {
	private final ProductOptionRepository productOptionRepository;

	@Transactional
	public List<OptionCodeResponse> getOptionList() {
		return productOptionRepository.findAllOptionCode();
	}

	@Transactional
	public List<String> getOptionDetailList(String optionCode) {
		return productOptionRepository.findDetailByOptionCode(optionCode);
	}
}
