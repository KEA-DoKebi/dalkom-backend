package com.dokebi.dalkom.domain.product.service;

import static com.dokebi.dalkom.domain.product.factory.ProductByCategoryDetailResponseFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ProductByCategoryResponseFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ProductCreateRequestFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.service.CategoryService;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.product.dto.OptionListDTO;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDTO;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.dto.StockListDTO;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.exception.ProductNotFoundException;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;

public class ProductServiceTest {
	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductStockService productStockService;

	@Mock
	private CategoryService categoryService;

	@Mock
	private ProductOptionService productOptionService;

	private ProductService productService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		productService = new ProductService(productRepository, productStockService, categoryService,
			productOptionService);
	}

	@Test
	@DisplayName("상품 정보 추가")
	public void createProductTest() {
		// Given: 주어진 상품 생성 요청 데이터
		ProductCreateRequest request = createProductCreateRequest();

		// Mock 설정
		given(categoryService.readCategoryBySeq(anyLong())).willReturn(
			new Category("메이크업", 1L, "Url"));
		given(productOptionService.readProductOptionByPrdtOptionSeq(anyLong())).willReturn(
			ProductOption.createProductOption());

		// When: 상품 생성 메서드 실행
		productService.createProduct(request);

		// Then: 상품이 저장소에 저장되었는지 확인
		then(productRepository).should().save(any(Product.class));
	}

	@Test
	@DisplayName("상품 상세 정보 조회")
	public void readProductByProductSeqTest() {
		// Given: 주어진 상품 ID
		Long productSeq = 1L;
		Category testCategory = new Category("메이크업", 1L, "Url");
		given(productRepository.findByProductSeq(productSeq)).willReturn(
			Optional.of(new Product(testCategory, "test", 5000,
				"info", "Url", "Company", "Y")));

		// When: 상품 조회 메서드 실행
		Product result = productService.readProductByProductSeq(productSeq);

		// Then: 반환된 상품 확인
		assertNotNull(result);
	}

	@Test
	@DisplayName("상품 상세 정보 조회 - 예외 발생")
	public void readProductByProductSeqNotFoundExceptionTest() {
		// Given: 존재하지 않는 상품 ID
		Long productSeq = 1L;
		given(productRepository.findByProductSeq(productSeq)).willReturn(Optional.empty());

		// When & Then: 예외가 발생하는지 확인
		assertThrows(ProductNotFoundException.class, () -> productService.readProductByProductSeq(productSeq));
	}

	@Test
	@DisplayName("카테고리 별 상품 목록 조회")
	public void readProductListByCategoryTest() {
		// Given: 카테고리 ID와 페이지 정보
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		List<ProductByCategoryResponse> productByCategoryResponseList
			= createProductByCategoryResponseList();

		given(productRepository.findProductListByCategory(categorySeq, pageable)).willReturn(
			new PageImpl<>(productByCategoryResponseList, pageable, productByCategoryResponseList.size()));

		// When: 카테고리 별 상품 목록 조회 메서드 실행
		Page<ProductByCategoryResponse> result = productService.readProductListByCategory(categorySeq, pageable);

		// Then: 반환된 상품 목록 확인
		assertThat(result).isNotNull();
	}

	@Test
	@DisplayName("카테고리 별 상품 목록 조회 - 예외 발생")
	public void readProductListByCategoryNotFoundExceptionTest() {
		// Given: 존재하지 않는 카테고리 ID
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		given(productRepository.findProductListByCategory(categorySeq, pageable)).willReturn(null);

		// When & Then: 예외가 발생하는지 확인
		assertThrows(ProductNotFoundException.class,
			() -> productService.readProductListByCategory(categorySeq, pageable));
	}

	@Test
	@DisplayName("세부 카테고리 별 상품 목록 조회")
	public void readProductListByCategoryDetailTest() {
		// Given: 카테고리 세부 ID와 페이지 정보
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		List<ProductByCategoryDetailResponse> productByCategoryResponseList
			= createProductByCategoryDetailResponseList();

		given(productRepository.findProductListByCategoryDetail(categorySeq, pageable)).willReturn(
			new PageImpl<>(productByCategoryResponseList, pageable, productByCategoryResponseList.size()));

		// When: 카테고리 세부 별 상품 목록 조회 메서드 실행
		Page<ProductByCategoryDetailResponse> result
			= productService.readProductListByCategoryDetail(categorySeq, pageable);

		// Then: 반환된 상품 목록 확인
		assertThat(result).isNotNull();
	}

	@Test
	@DisplayName("세부 카테고리 별 상품 목록 조회 - 예외 발생")
	public void readProductListByCategoryDetailNotFoundExceptionTest() {
		// Given: 카테고리 세부 ID와 페이지 정보
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		List<ProductByCategoryDetailResponse> productByCategoryResponseList
			= createProductByCategoryDetailResponseList();

		given(productRepository.findProductListByCategoryDetail(categorySeq, pageable)).willReturn(null);

		// When & Then: 예외가 발생하는지 확인
		assertThrows(ProductNotFoundException.class,
			() -> productService.readProductListByCategoryDetail(categorySeq, pageable));
	}

	@Test
	@DisplayName("관리자용 상품 리스트 조회")
	public void readProductTest() {
		// Given: 상품 ID
		Long productSeq = 1L;
		given(productRepository.findProductDetailBySeq(productSeq)).willReturn(new ReadProductDetailDTO());
		given(productRepository.findStockListBySeq(productSeq)).willReturn(List.of(new StockListDTO()));
		given(productRepository.findOptionListBySeq(productSeq)).willReturn(List.of(new OptionListDTO()));
		given(productRepository.findProductImageBySeq(productSeq)).willReturn(List.of("imageURL"));

		// When: 상품 상세 정보 조회 메서드 실행
		ReadProductDetailResponse result = productService.readProduct(productSeq);

		// Then: 반환된 상품 상세 정보 확인
		assertThat(result).isNotNull();
	}

	@Test
	@DisplayName("관리자용 상품 리스트 조회 - 예외 발생")
	public void readProductNotFoundExceptionTest() {
		// Given: 상품 ID
		Long productSeq = 1L;

		given(productRepository.findStockListBySeq(anyLong())).willReturn(null);
		given(productRepository.findOptionListBySeq(anyLong())).willReturn(null);
		given(productRepository.findProductImageBySeq(anyLong())).willReturn(null);

		// When & Then: 예외가 발생하는지 확인
		assertThrows(ProductNotFoundException.class,
			() -> productService.readProduct(productSeq));
	}

	// 전체 상품 목록 조회
	@Test
	public void readProductListTest() {
		// Given: 페이지 정보
		PageRequest pageable = PageRequest.of(0, 8);
		given(productRepository.findProductList(pageable)).willReturn(Page.empty());

		// When: 전체 상품 목록 조회 메서드 실행
		Page<ReadProductResponse> result = productService.readProductList(pageable);

		// Then: 반환된 상품 목록 확인
		assertThat(result).isNotNull();
	}
}
