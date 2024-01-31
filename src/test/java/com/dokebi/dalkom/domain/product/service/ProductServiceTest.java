package com.dokebi.dalkom.domain.product.service;

import static com.dokebi.dalkom.domain.product.factory.ProductByCategoryDetailResponseFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ProductByCategoryResponseFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ProductCreateRequestFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.service.CategoryService;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDto;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.exception.ProductNotFoundException;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
import com.dokebi.dalkom.domain.stock.dto.StockListDto;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;

@ExtendWith(MockitoExtension.class)
@Transactional(readOnly = true)
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
	private ProductOptionService productOptionService;

	// PRODUCT-001 - 상위 카테고리 상품 리스트 조회 테스트
	@Test
	@DisplayName("상위 카테고리 상품 리스트 조회 테스트")
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

	// PRODUCT-001 - 상위 카테고리 상품 리스트 조회 테스트
	@Test
	@DisplayName("상위 카테고리 상품 리스트 조회 테스트 - 예외 발생")
	public void readProductListByCategoryNotFoundExceptionTest() {
		// Given: 존재하지 않는 카테고리 ID
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);

		given(productRepository.findProductListByCategory(categorySeq, pageable)).willReturn(null);

		// When & Then: 예외가 발생하는지 확인
		assertThrows(ProductNotFoundException.class,
			() -> productService.readProductListByCategory(categorySeq, pageable));
	}

	// PRODUCT-002 상품 상세 정보 조회 테스트
	@Test
	@DisplayName("상품 상세 정보 조회 테스트")
	public void readProductTest() {
		// Given: 상품 ID
		Long productSeq = 1L;
		given(productRepository.findProductDetailByProductSeq(productSeq)).willReturn(new ReadProductDetailDto());
		given(productStockService.readStockListDtoByProductSeq(productSeq)).willReturn(List.of(new StockListDto()));
		given(productRepository.findProductImageByProductSeq(productSeq)).willReturn(List.of("imageURL"));

		// When: 상품 상세 정보 조회 메서드 실행
		ReadProductDetailResponse result = productService.readProduct(productSeq);

		// Then: 반환된 상품 상세 정보 확인
		assertThat(result).isNotNull();
	}

	// PRODUCT-002 상품 상세 정보 조회 테스트
	@Test
	@DisplayName("관리자용 상품 리스트 조회 - 예외 발생")
	public void readProductNotFoundExceptionTest() {
		// Given: 상품 ID
		Long productSeq = 1L;

		given(productStockService.readStockListDtoByProductSeq(anyLong())).willReturn(null);
		given(productRepository.findProductImageByProductSeq(anyLong())).willReturn(null);

		// When & Then: 예외가 발생하는지 확인
		assertThrows(ProductNotFoundException.class,
			() -> productService.readProduct(productSeq));
	}

	// PRODUCT-003 - 상품 정보 추가 테스트
	@Test
	@Transactional
	@DisplayName("상품 정보 추가 테스트")
	public void createProductTest() {
		// Given: 주어진 상품 생성 요청 데이터
		ProductCreateRequest request = createProductCreateRequest();

		// Mock 설정
		given(categoryService.readCategoryByCategorySeq(anyLong())).willReturn(
			new Category("메이크업", 1L, "Url"));
		given(productOptionService.readProductOptionByPrdtOptionSeq(anyLong()))
			.willReturn(ProductOption.createProductOption());

		// When: 상품 생성 메서드 실행
		productService.createProduct(request);

		// Then: 상품이 저장소에 저장되었는지 확인
		then(productRepository).should().save(any(Product.class));
	}

	// PRODUCT-004 - 상품 리스트 조회(관리자 화면) 테스트
	@Test
	@DisplayName("상품 리스트 조회(관리자 화면) 테스트")
	public void readProductByProductSeqTest() {
		// Given: 주어진 상품 ID
		Long productSeq = 1L;
		Category testCategory = new Category("메이크업", 1L, "Url");
		given(productRepository.findProductByProductSeq(productSeq)).willReturn(
			Optional.of(new Product(testCategory, "test", 5000,
				"info", "Url", "Company", "Y")));

		// When: 상품 조회 메서드 실행
		Product result = productService.readProductByProductSeq(productSeq);

		// Then: 반환된 상품 확인
		assertNotNull(result);
	}

	// PRODUCT-005 - 하위 카테고리 별 상품 목록 조회 테스트
	@Test
	@DisplayName("하위 카테고리 별 상품 목록 조회 테스트")
	public void readProductListByDetailCategoryTest() {
		// Given: 카테고리 세부 ID와 페이지 정보
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		List<ProductByCategoryDetailResponse> productByCategoryResponseList
			= createProductByCategoryDetailResponseList();

		given(productRepository.findProductListByDetailCategory(categorySeq, pageable)).willReturn(
			new PageImpl<>(productByCategoryResponseList, pageable, productByCategoryResponseList.size()));

		// When: 카테고리 세부 별 상품 목록 조회 메서드 실행
		Page<ProductByCategoryDetailResponse> result
			= productService.readProductListByDetailCategory(categorySeq, pageable);

		// Then: 반환된 상품 목록 확인
		assertThat(result).isNotNull();
	}

	// PRODUCT-005 - 하위 카테고리 별 상품 목록 조회 테스트
	@Test
	@DisplayName("세부 카테고리 별 상품 목록 조회 - 예외 발생")
	public void readProductListByDetailCategoryNotFoundExceptionTest() {
		// Given: 카테고리 세부 ID와 페이지 정보
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);

		given(productRepository.findProductListByDetailCategory(categorySeq, pageable)).willReturn(null);

		// When & Then: 예외가 발생하는지 확인
		assertThrows(ProductNotFoundException.class,
			() -> productService.readProductListByDetailCategory(categorySeq, pageable));
	}

	// PRODUCT-006 - 메인 페이지 전체 카테고리 별 상품 목록 조회 테스트
	@Test
	@DisplayName("메인 페이지 전체 카테고리 별 상품 목록 조회 테스트")
	public void readProductListTest() {
		// Given: 페이지 정보
		PageRequest pageable = PageRequest.of(0, 8);
		given(productRepository.findAdminPageProductList(pageable)).willReturn(Page.empty());

		// When: 전체 상품 목록 조회 메서드 실행
		Page<ReadProductResponse> result = productService.readAdminPageProductList(pageable);

		// Then: 반환된 상품 목록 확인
		assertThat(result).isNotNull();
	}

	// 다른 Domain Service에서 사용하도록 하는 메소드 테스트
	@Test
	@DisplayName("다른 Domain Service에서 사용하도록 하는 메소드 테스트")
	public void readProductByProductSeqNotFoundExceptionTest() {
		// Given: 존재하지 않는 상품 ID
		Long productSeq = 1L;
		given(productRepository.findProductByProductSeq(productSeq)).willReturn(Optional.empty());

		// When & Then: 예외가 발생하는지 확인
		assertThrows(ProductNotFoundException.class, () -> productService.readProductByProductSeq(productSeq));
	}
}
