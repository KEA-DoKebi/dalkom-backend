package com.dokebi.dalkom.domain.product.service;

<<<<<<< HEAD
import static com.dokebi.dalkom.domain.category.factory.CategoryFactory.*;
import static com.dokebi.dalkom.domain.category.factory.CategoryResponseFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ProductByCategoryDetailResponseFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ProductFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ProductUpdateRequestFactory.*;
import static com.dokebi.dalkom.domain.stock.factory.StockFactory.*;
=======
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
<<<<<<< HEAD
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.common.magicnumber.ProductActiveState;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.service.CategoryService;
import com.dokebi.dalkom.domain.chat.service.ChatGptService;
import com.dokebi.dalkom.domain.option.dto.OptionAmountDto;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailPage;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ProductCompareDetailDto;
import com.dokebi.dalkom.domain.product.dto.ProductCompareDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.dto.ProductUpdateRequest;
=======

import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.service.CategoryService;
import com.dokebi.dalkom.domain.option.dto.OptionAmountDto;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDto;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.exception.ProductNotFoundException;
import com.dokebi.dalkom.domain.product.factory.OptionAmountDtoListFactory;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
<<<<<<< HEAD
import com.dokebi.dalkom.domain.review.dto.ReviewSimpleDto;
import com.dokebi.dalkom.domain.review.service.ReviewService;
=======
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043
import com.dokebi.dalkom.domain.stock.dto.StockListDto;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
<<<<<<< HEAD
	@Mock
	Pageable pageable;
	@InjectMocks
	private ProductService productService;
=======
	@InjectMocks
	private ProductService productService;

>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductStockService productStockService;
	@Mock
	private CategoryService categoryService;
	@Mock
	private ProductOptionService optionService;
<<<<<<< HEAD
	@Mock
	private ReviewService reviewService;
	@Mock
	private ChatGptService chatGptService;
=======
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043

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
<<<<<<< HEAD
	@DisplayName("상위 카테고리 상품 리스트 조회 테스트 - 결과없음")
	void readProductListByCategoryFailTest() {
		// Given
		Long categorySeq = 1L;
		given(productRepository.findProductListByCategory(eq(categorySeq), eq(pageable))).willReturn(
			new PageImpl<>(List.of()));

		// When / Then
		assertThrows(ProductNotFoundException.class,
			() -> productService.readProductListByCategory(categorySeq, pageable));
	}

	@Test
=======
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043
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
<<<<<<< HEAD
	@DisplayName("상품 상세 정보 조회 - 예외 발생 테스트")
	void readProductNotFoundExceptionTest() {
		// Given
		Long productSeq = 1L;
		given(productRepository.findProductDetailByProductSeq(eq(productSeq))).willReturn(null);

		// When & Then
		assertThrows(ProductNotFoundException.class, () -> productService.readProduct(productSeq));
	}

	@Test
=======
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043
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
<<<<<<< HEAD
	@DisplayName("하위 카테고리 별 상품 목록 조회")
	void readProductListByDetailCategory_Success() {
		// Given
		Long categorySeq = 1L;
		Category mockCategory = new Category("Test Category", categorySeq, "Category Image URL");
		List<ProductByCategoryDetailPage> mockProductList = createProductByCategoryDetailResponseList();
		Page<ProductByCategoryDetailPage> mockPage = new PageImpl<>(mockProductList);

		given(categoryService.readCategoryByCategorySeq(categorySeq)).willReturn(mockCategory);
		given(productRepository.findProductListByDetailCategory(eq(categorySeq), any(Pageable.class)))
			.willReturn(mockPage);

		// When
		ProductByCategoryDetailResponse response = productService.readProductListByDetailCategory(categorySeq,
			pageable);

		// Then
		assertNotNull(response);
		assertFalse(response.getPage().isEmpty()); // 반환된 페이지에 상품이 실제로 포함되어 있는지 확인
		assertEquals(mockCategory.getName(), response.getCategoryName()); // 카테고리 이름이 예상과 일치하는지 확인
	}

	@Test
	@DisplayName("하위 카테고리 별 상품 목록 조회 - 상품이 없을 때")
	void readProductListByDetailCategory_ProductNotFoundException() {
		// Given
		Long categorySeq = 1L;
		given(categoryService.readCategoryByCategorySeq(categorySeq)).willReturn(createMockCategory());
		given(productRepository.findProductListByDetailCategory(eq(categorySeq), any(Pageable.class)))
			.willReturn(Page.empty());

		// When & Then
		assertThrows(ProductNotFoundException.class,
			() -> productService.readProductListByDetailCategory(categorySeq, pageable));
	}

	@Test
	@DisplayName("전체 카테고리 별 상품 목록 조회 - 메인 화면")
	void readProductListByCategoryAll_Success() {
		// Given
		given(categoryService.readCategoryList()).willReturn(List.of(createCategoryResponse(1L, "이름", "URL")));
		given(productRepository.findProductListByCategoryAll(anyLong(), any(Pageable.class)))
			.willReturn(new PageImpl<>(List.of()));

		// When
		var result = productService.readProductListByCategoryAll(pageable);

		// Then
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	@DisplayName("특정 상품 정보 수정")
	void updateProduct_Success() {
		// Given
		Long productSeq = 13L;
		Product product = createMockProduct();
		given(productRepository.findProductByProductSeq(productSeq)).willReturn(Optional.of(product));
		given(productStockService.readStockByProductAndOptionSeq(anyLong(), anyLong()))
			.willReturn(createMockStock(product));

		// When
		productService.updateProduct(productSeq, createProductUpdateRequest());

		// Then
		assertEquals(5000, product.getPrice());
	}

	@Test
	@DisplayName("특정 상품 정보 수정 - 상품이 존재하지 않을 때")
	void updateProduct_ProductNotFoundException() {
		// Given
		Long productSeq = 1L;
		given(productRepository.findProductByProductSeq(productSeq)).willReturn(Optional.empty());

		// When & Then
		assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productSeq,
			new ProductUpdateRequest()));
	}

	@Test
	@DisplayName("상품 리스트 검색")
	void readProductListSearchByNameTest() {
		// Given
		given(productRepository.findProductListSearchByName(anyString(), any(Pageable.class)))
			.willReturn(new PageImpl<>(List.of()));

		// When
		Page<ReadProductResponse> result = productService.readProductListSearch(
			"test", null, pageable);

		// Then
		assertNotNull(result);
	}

	@Test
	@DisplayName("특정 상품 정보 (상품 비교용) 조회")
	void readProductCompareDetailByProductSeq_Success() {
		// Given
		Long productSeq = 1L;
		given(productRepository.readProductCompareDetailByProductSeq(productSeq)).willReturn(
			Optional.of(new ProductCompareDetailDto()));
		given(reviewService.readReviewSimpleByProductSeq(anyLong())).willReturn(List.of(new ReviewSimpleDto(5, "최고")));
		given(chatGptService.processChatGptRequest(anyList())).willReturn("최고라고 합니다.");

		// When
		ProductCompareDetailResponse result = productService.readProductCompareDetailByProductSeq(productSeq);

		// Then
		assertNotNull(result);
	}

	@Test
	@DisplayName("특정 상품 정보 (상품 비교용) 조회 - 상품이 존재하지 않을 때")
	void readProductCompareDetailByProductSeq_ProductNotFoundException() {
		// Given
		Long productSeq = 1L;
		given(productRepository.readProductCompareDetailByProductSeq(productSeq)).willReturn(Optional.empty());

		// When & Then
		assertThrows(ProductNotFoundException.class,
			() -> productService.readProductCompareDetailByProductSeq(productSeq));
	}

	@Test
	void readProductByProductSeq_Success() {
		// Given
		Long productSeq = 1L;
		Product expectedProduct = createMockProduct();
		given(productRepository.findProductByProductSeq(productSeq)).willReturn(Optional.of(expectedProduct));

		// When
		Product result = productService.readProductByProductSeq(productSeq);

		// Then
		assertNotNull(result);
		assertEquals(expectedProduct, result);
	}

	@Test
	void checkProductActiveState_Success() {
		// Given
		Long productSeq = 1L;
		Product product = createMockProduct();
		product.setState("ACTIVE");
		given(productRepository.findProductByProductSeq(productSeq)).willReturn(Optional.of(product));

		// When
		String state = productService.checkProductActiveState(productSeq);

		// Then
		assertNotNull(state);
		assertEquals("ACTIVE", state);
	}

	@Test
	void soldoutProductByProductSeq_Success() {
		// Given
		Long productSeq = 1L;
		Product product = createMockProduct();
		given(productRepository.findProductByProductSeq(productSeq)).willReturn(Optional.of(product));

		// When
		productService.soldoutProductByProductSeq(productSeq);

		// Then
		assertEquals(ProductActiveState.SOLDOUT.getState(), product.getState());
	}

	@Test
	void activeProductByProductSeq_Success() {
		// Given
		Long productSeq = 1L;
		Product product = createMockProduct();
		given(productRepository.findProductByProductSeq(productSeq)).willReturn(Optional.of(product));

		// When
		productService.activeProductByProductSeq(productSeq);

		// Then
		assertEquals(ProductActiveState.ACTIVE.getState(), product.getState());
	}

	@Test
	void readProductByProductSeq_NotFound() {
		// Given
		Long productSeq = 1L;
		given(productRepository.findProductByProductSeq(productSeq)).willReturn(Optional.empty());

		// When & Then
		assertThrows(ProductNotFoundException.class, () -> productService.readProductByProductSeq(productSeq));
=======
	@DisplayName("상품 상세 정보 조회 - 예외 발생 테스트")
	void readProductNotFoundExceptionTest() {
		// Given
		Long productSeq = 1L;
		given(productRepository.findProductDetailByProductSeq(eq(productSeq))).willReturn(null);

		// When & Then
		assertThrows(ProductNotFoundException.class, () -> productService.readProduct(productSeq));
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043
	}
}
