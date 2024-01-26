package com.dokebi.dalkom.domain.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicNumber.ProductActiveState;
import com.dokebi.dalkom.domain.category.dto.CategoryResponse;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.service.CategoryService;
import com.dokebi.dalkom.domain.option.dto.OptionAmountDto;
import com.dokebi.dalkom.domain.option.dto.OptionListDto;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.dto.ProductMainResponse;
import com.dokebi.dalkom.domain.product.dto.ProductUpdateRequest;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDto;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.exception.ProductNotFoundException;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
import com.dokebi.dalkom.domain.stock.dto.StockListDto;
import com.dokebi.dalkom.domain.stock.entity.ProductStock;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final ProductStockService productStockService;
	private final CategoryService categoryService;
	private final ProductOptionService productOptionService;

	// PRODUCT-001 - 상위 카테고리로 상품 리스트 조회
	public Page<ProductByCategoryResponse> readProductListByCategory(Long categorySeq, Pageable pageable) {
		// 카테고리Seq 를 통해 상품 조회
		Page<ProductByCategoryResponse> productList = productRepository.findProductListByCategory(categorySeq,
			pageable);

		// 조회 결과 검사
		if (productList == null || productList.isEmpty()) {
			throw new ProductNotFoundException();
		}

		return productList;
	}

	// PRODUCT-002 (상품 상세 정보 조회)
	public ReadProductDetailResponse readProduct(Long productSeq) {
		// responseBody에 필요한 값들을 탐색
		ReadProductDetailDto productDetailDto = productRepository.findProductDetailBySeq(productSeq);
		List<StockListDto> stockList = productStockService.readStockListDtoByProductSeq(productSeq);
		List<OptionListDto> optionList = productOptionService.readOptionListDtoByProductSeq(productSeq);
		List<String> productImageUrlList = productRepository.findProductImageBySeq(productSeq);

		// 조회 결과 검사
		if (stockList == null || optionList == null || stockList.isEmpty() || optionList.isEmpty()) {
			throw new ProductNotFoundException();
		}

		// response용 DTO에 담아서 return
		return new ReadProductDetailResponse(productDetailDto, optionList, stockList, productImageUrlList);
	}

	// PRODUCT-003 (상품 정보 추가)
	@Transactional
	public void createProduct(ProductCreateRequest request) {
		// 외래키에 저장될 엔티티 탐색
		Category category = categoryService.readCategoryByCategorySeq(request.getCategorySeq());

		// 새 Product 생성
		Product newProduct = new Product(category, request.getName(), request.getPrice(), request.getInfo(),
			request.getImageUrl(), request.getCompany(), request.getState());

		// 상품 저장
		productRepository.save(newProduct);

		// 옵션에 따른 재고 등록
		for (OptionAmountDto optionAmountDto : request.getPrdtOptionList()) {
			ProductOption option = productOptionService.readProductOptionByPrdtOptionSeq(
				optionAmountDto.getPrdtOptionSeq());

			ProductStock initialStock = new ProductStock(newProduct, option, optionAmountDto.getAmount());

			productStockService.createStock(initialStock);
		}
	}

	// PRODUCT-004 (상품 리스트 조회 - 관리자 화면)
	public Page<ReadProductResponse> readAdminPageProductList(Pageable pageable) {
		return productRepository.findAdminPageProductList(pageable);
	}

	// PRODUCT-005 (하위 카테고리 별 상품 목록 조회)
	public Page<ProductByCategoryDetailResponse> readProductListByDetailCategory(Long categorySeq, Pageable pageable) {
		// 탐색
		Page<ProductByCategoryDetailResponse> productList = productRepository.findProductListByDetailCategory(
			categorySeq, pageable);

		// 결과 검사
		if (productList == null || productList.isEmpty()) {
			throw new ProductNotFoundException();
		}

		// 결과 전송
		return productList;
	}

	// PRODUCT-006 (전체 카테고리 별 상품 목록 조회 - 메인 화면)
	public Map<String, List<ProductMainResponse>> readProductListByCategoryAll(Pageable pageable) {
		Map<String, List<ProductMainResponse>> categoryMap = new HashMap<>();
		List<CategoryResponse> categoryList = categoryService.readCategoryList();

		// 상위 카테고리 각각에 대한 상품 담기
		for (CategoryResponse categoryResponse : categoryList) {
			Page<ProductMainResponse> page = productRepository.findProductListByCategoryAll(
				categoryResponse.getCategorySeq(), pageable);
			// Page 객체에서 List 추출 후 Map에 추가
			categoryMap.put(categoryResponse.getName(), page.getContent());
		}
		return categoryMap;
	}

	/** 다른 Domain Service에서 사용할 메소드 **/
	public Product readProductByProductSeq(Long productSeq) {
		return productRepository.findByProductSeq(productSeq).orElseThrow(ProductNotFoundException::new);
	}

	public void updateProduct(Long productSeq, ProductUpdateRequest request) {
		Product product = productRepository.findByProductSeq(productSeq).orElseThrow(ProductNotFoundException::new);

		//상품 정보 저장
		product.setCategory(categoryService.readCategoryByCategorySeq(request.getCategorySeq()));
		product.setName(request.getName());
		product.setPrice(request.getPrice());
		product.setInfo(request.getInfo());
		product.setImageUrl(request.getImageUrl());
		product.setCompany(request.getCompany());
		product.setState(request.getState());

		for (OptionAmountDto optionAmountDto : request.getOpitonAmountList()) {
			ProductStock stock = productStockService.readStockByProductAndOptionSeq(productSeq,
				optionAmountDto.getPrdtOptionSeq());

			// updateStock은 History를 남기는 메서드이므로, 재고가 다를 경우에만 실행하기
			if (!Objects.equals(stock.getAmount(), optionAmountDto.getAmount())) {
				productStockService.updateStock(stock.getPrdtStockSeq(), optionAmountDto.getAmount());
			}
		}
	}

	public void inactiveProductBySeq(Long productSeq) {
		Product product = productRepository.findByProductSeq(productSeq).orElseThrow(ProductNotFoundException::new);

		product.setState(ProductActiveState.INACTIVE);
	}
}
