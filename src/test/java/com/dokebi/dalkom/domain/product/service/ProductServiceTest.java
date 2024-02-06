package com.dokebi.dalkom.domain.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.service.CategoryService;
import com.dokebi.dalkom.domain.option.dto.OptionAmountDto;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDto;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.exception.ProductNotFoundException;
import com.dokebi.dalkom.domain.product.factory.OptionAmountDtoListFactory;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
import com.dokebi.dalkom.domain.stock.dto.StockListDto;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductStockService productStockService;
	@Mock
	private CategoryService categoryService;
	@Mock
	private ProductOptionService optionService;

	@Test
	@DisplayName("상위 카테고리 상품 리스트 조회 테스트")
	void readProductListByCategoryTest() {
		// Given
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 10);
		List<ProductByCategoryResponse> responses = List.of(new ProductByCategoryResponse());
		Page<ProductByCategoryResponse> expectedPage = new PageImpl<>(responses, pageable, responses.size());
		given(productRepository.findProductListByCategory(eq(categorySeq), eq(pageable))).willReturn(expectedPage);

		// When
		Page<ProductByCategoryResponse> result = productService.readProductListByCategory(categorySeq, pageable);

		// Then
		assertNotNull(result);
		assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
	}

	@Test
	@DisplayName("상품 상세 정보 조회 테스트")
	void readProductTest() {
		// Given
		Long productSeq = 1L;
		ReadProductDetailDto productDetailDto = new ReadProductDetailDto();
		List<StockListDto> stockList = List.of(new StockListDto());
		List<String> imageUrls = List.of("image1", "image2");
		given(productRepository.findProductDetailByProductSeq(eq(productSeq))).willReturn(productDetailDto);
		given(productStockService.readStockListDtoByProductSeq(eq(productSeq))).willReturn(stockList);
		given(productRepository.findProductImageByProductSeq(eq(productSeq))).willReturn(imageUrls);

		// When
		ReadProductDetailResponse result = productService.readProduct(productSeq);

		// Then
		assertNotNull(result);
	}

	@Test
	@DisplayName("상품 정보 추가 테스트")
	void createProductTest() {
		// Given
		ProductCreateRequest request = new ProductCreateRequest();

		Long categorySeq = 1L; // 예제 카테고리 시퀀스
		Category expectedCategory = new Category("카테고리 이름", categorySeq, "카테고리 이미지 URL");
		List<OptionAmountDto> productOptionList = OptionAmountDtoListFactory.createList();
		ProductOption productOption = ProductOption.createProductOption();

		request.setCategorySeq(categorySeq);
		request.setPrdtOptionList(productOptionList);

		given(categoryService.readCategoryByCategorySeq(anyLong())).willReturn(expectedCategory);
		given(optionService.readProductOptionByPrdtOptionSeq(anyLong())).willReturn(productOption);
		// When
		productService.createProduct(request);

		// Then
		verify(productRepository).save(any(Product.class));
	}

	@Test
	@DisplayName("상품 리스트 조회 - 관리자 화면 테스트")
	void readAdminPageProductListTest() {
		// Given
		PageRequest pageable = PageRequest.of(0, 10);
		Page<ReadProductResponse> expectedPage = new PageImpl<>(List.of(new ReadProductResponse()), pageable, 1);
		given(productRepository.findAdminPageProductList(pageable)).willReturn(expectedPage);

		// When
		Page<ReadProductResponse> result = productService.readAdminPageProductList(pageable);

		// Then
		assertNotNull(result);
		assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
	}

	@Test
	@DisplayName("상품 상세 정보 조회 - 예외 발생 테스트")
	void readProductNotFoundExceptionTest() {
		// Given
		Long productSeq = 1L;
		given(productRepository.findProductDetailByProductSeq(eq(productSeq))).willReturn(null);

		// When & Then
		assertThrows(ProductNotFoundException.class, () -> productService.readProduct(productSeq));
	}
}
