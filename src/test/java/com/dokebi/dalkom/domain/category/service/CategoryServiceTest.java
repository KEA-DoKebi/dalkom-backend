package com.dokebi.dalkom.domain.category.service;

import static com.dokebi.dalkom.domain.category.factory.CategoryResponseFactory.*;
import static com.dokebi.dalkom.domain.category.factory.SubCategoryResponseFactory.*;
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

import com.dokebi.dalkom.domain.category.dto.CategoryResponse;
import com.dokebi.dalkom.domain.category.dto.SubCategoryResponse;
import com.dokebi.dalkom.domain.category.exception.CategoryNotFoundException;
import com.dokebi.dalkom.domain.category.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
	@InjectMocks
	private CategoryService categoryService;
	@Mock
	private CategoryRepository categoryRepository;

	@Test
	void readCategoryListTest() {
		List<CategoryResponse> fakeCategories = Arrays.asList(
			createCategoryResponse(1L, "의류", "image.url"),
			createCategoryResponse(2L, "음식", "image.url")
		);

		// Mock 객체에 대한 행동 정의
		when(categoryService.readCategoryList()).thenReturn(fakeCategories);

		// 서비스 메서드 호출
		List<CategoryResponse> categoryResponses = categoryService.readCategoryList();

		// 반환된 결과 검증
		assertEquals(fakeCategories.size(), categoryResponses.size());
	}

	@Test
	void readSubCategoryListTest() {
		Long categorySeq = 1L;
		List<SubCategoryResponse> subFakeCategories = Arrays.asList(
			createSubCategoryResponse(categorySeq, "여성 의류"),
			createSubCategoryResponse(categorySeq, "남성 의류")
		);
		when(categoryService.readSubCategoryList(categorySeq)).thenReturn(subFakeCategories);

		List<SubCategoryResponse> subCategoryResponses = categoryService.readSubCategoryList(categorySeq);

		assertEquals(subFakeCategories.size(), subCategoryResponses.size());

	}

	@Test
	void readCategoryByCategorySeqNotFoundTest() {
		Long categorySeq = 1L;

		// Mock repository의 동작 설정 (Optional.empty() 반환)
		when(categoryRepository.findCategoryByCategorySeq(categorySeq)).thenReturn(Optional.empty());

		// 예외가 발생하는지 검증
		assertThrows(CategoryNotFoundException.class, () -> categoryService.readCategoryByCategorySeq(categorySeq));

		// Mock repository 메서드가 호출되었는지 검증
		verify(categoryRepository, times(1)).findCategoryByCategorySeq(categorySeq);
	}

}
