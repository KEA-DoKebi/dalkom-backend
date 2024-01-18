package com.dokebi.dalkom.domain.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.category.dto.CategoryResponse;
import com.dokebi.dalkom.domain.category.dto.SubCategoryResponse;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.exception.CategoryNotFoundException;
import com.dokebi.dalkom.domain.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<CategoryResponse> readCategoryList() {
		return categoryRepository.findCategoryList();
	}

	public List<SubCategoryResponse> readSubCategoryList(Long categorySeq) {
		return categoryRepository.findSubCategoryList(categorySeq);
	}

	public Category readCategoryByCategorySeq(Long categorySeq) {
		return categoryRepository.findByCategorySeq(categorySeq).orElseThrow(CategoryNotFoundException::new);
	}

	@Transactional
	public Category readCategoryBySeq(Long categorySeq) {
		return categoryRepository.findById(categorySeq).orElseThrow(CategoryNotFoundException::new);
	}
}
