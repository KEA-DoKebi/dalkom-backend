package com.dokebi.dalkom.domain.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicnumber.ProductActiveState;
import com.dokebi.dalkom.domain.category.dto.CategoryResponse;
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
import com.dokebi.dalkom.domain.product.dto.ProductMainResponse;
import com.dokebi.dalkom.domain.product.dto.ProductSearchCondition;
import com.dokebi.dalkom.domain.product.dto.ProductUpdateRequest;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDto;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.exception.ProductNotFoundException;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
import com.dokebi.dalkom.domain.review.dto.ReviewSimpleDto;
import com.dokebi.dalkom.domain.review.service.ReviewService;
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
	private final ReviewService reviewService;
	private final ChatGptService chatGptService;

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
		ReadProductDetailDto productDetailDto = productRepository.findProductDetailByProductSeq(productSeq);
		List<StockListDto> stockList = productStockService.readStockListDtoByProductSeq(productSeq);
		List<String> productImageUrlList = productRepository.findProductImageByProductSeq(productSeq);

		// 조회 결과 검사
		if (stockList == null || stockList.isEmpty()) {
			throw new ProductNotFoundException();
		}

		// response용 DTO에 담아서 return
		return new ReadProductDetailResponse(productDetailDto, stockList, productImageUrlList);
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
	public ProductByCategoryDetailResponse readProductListByDetailCategory(Long categorySeq, Pageable pageable) {
		// 탐색
		Category category = categoryService.readCategoryByCategorySeq(categorySeq);
		Page<ProductByCategoryDetailPage> productPage = productRepository.findProductListByDetailCategory(
			categorySeq, pageable);

		// 결과 검사
		if (productPage == null || productPage.isEmpty()) {
			throw new ProductNotFoundException();
		}

		// 결과 전송
		return new ProductByCategoryDetailResponse(category.getName(), productPage);
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

	// PRODUCT-007 (특정 상품 정보 수정)
	@Transactional
	public void updateProduct(Long productSeq, ProductUpdateRequest request) {
		Product product = productRepository.findProductByProductSeq(productSeq)
			.orElseThrow(ProductNotFoundException::new);

		// 상품 정보 저장
		product.setCategory(categoryService.readCategoryByCategorySeq(request.getCategorySeq()));
		product.setName(request.getName());
		product.setPrice(request.getPrice());
		product.setInfo(request.getInfo());
		product.setImageUrl(request.getImageUrl());
		product.setCompany(request.getCompany());
		product.setState(request.getState());

		for (OptionAmountDto optionAmountDto : request.getOptionAmountList()) {
			ProductStock stock = productStockService.readStockByProductAndOptionSeq(productSeq,
				optionAmountDto.getPrdtOptionSeq());

			// updateStock은 History를 남기는 메서드이므로, 재고가 다를 경우에만 실행하기
			if (!Objects.equals(stock.getAmount(), optionAmountDto.getAmount())) {
				productStockService.updateStockByStockSeq(stock.getPrdtStockSeq(), optionAmountDto.getAmount());
			}
		}
	}

	// PRODUCT-009 (상품 리스트 검색)
	public Page<ReadProductResponse> readProductListSearch(String name, String company, Pageable pageable) {
		if (name != null) {
			return productRepository.findProductListSearchByName(name, pageable);
		} else if (company != null) {
			return productRepository.findProductListSearchByCompany(company, pageable);
		} else {
			return productRepository.findProductListSearch(pageable);
		}
	}

	// PRODUCT-010 (특정 상품 정보 (상품 비교용) 조회)
	public ProductCompareDetailResponse readProductCompareDetailByProductSeq(Long productSeq) {
		ProductCompareDetailDto productCompareDetailDto = productRepository.readProductCompareDetailByProductSeq(
			productSeq).orElseThrow(ProductNotFoundException::new);

		List<ReviewSimpleDto> reviewSimpleDtoList = reviewService.readReviewSimpleByProductSeq(productSeq);
		List<ReviewSimpleDto> positiveReview = new ArrayList<>();
		List<ReviewSimpleDto> negativeReview = new ArrayList<>();
		String positiveReviewSummery;
		String negativeReviewSummery;
		int listLength = reviewSimpleDtoList.size();
		double avgRating = 0;

		for (ReviewSimpleDto reviewSimpleDto : reviewSimpleDtoList) {
			// 평균 별점 계산
			avgRating += reviewSimpleDto.getRating();

			// 긍정 리뷰 & 부정 리뷰 샘플 리스트 생성
			if (reviewSimpleDto.getRating() > 3 && positiveReview.size() < 5) {
				positiveReview.add(reviewSimpleDto);
			} else if (reviewSimpleDto.getRating() < 4 && negativeReview.size() < 5) {
				negativeReview.add(reviewSimpleDto);
			}
		}
		avgRating = (double)Math.round((avgRating / listLength * 100)) / 100;

		//리뷰 샘플 리스트 예외처리
		if (positiveReview.isEmpty()) {
			positiveReviewSummery = "칭찬 리뷰가 존재하지 않습니다.";
		} else {
			positiveReviewSummery = chatGptService.processChatGptRequest(positiveReview);
		}
		if (negativeReview.isEmpty()) {
			negativeReviewSummery = "불만 리뷰가 존재하지 않습니다.";
		} else {
			negativeReviewSummery = chatGptService.processChatGptRequest(negativeReview);
		}

		return new ProductCompareDetailResponse(productCompareDetailDto, avgRating, listLength,
			positiveReviewSummery, negativeReviewSummery);
	}

	// PRODUCT-011 상품 리스트 검색 메인 페이지
	public Page<ProductMainResponse> readProductListSearchMain(String name, Pageable pageable) {
		return productRepository.findProductListSearchUserByName(name, pageable);
		// Page 객체에서 List 추출 후 Map에 추가
	}

	// PRODUCT-006 (전체 카테고리 별 상품 목록 조회 - 메인 화면)

	/** 다른 Domain Service에서 사용할 메소드 **/
	public Product readProductByProductSeq(Long productSeq) {
		return productRepository.findProductByProductSeq(productSeq).orElseThrow(ProductNotFoundException::new);
	}

	public Page<ReadProductResponse> readProductListSearchQueryDsl(ProductSearchCondition condition,
		Pageable pageable) {
		return productRepository.searchProduct(condition, pageable);
	}

	public String checkProductActiveState(Long productSeq) {
		Product product = productRepository.findProductByProductSeq(productSeq)
			.orElseThrow(ProductNotFoundException::new);

		return product.getState();
	}

	public void soldoutProductByProductSeq(Long productSeq) {
		Product product = productRepository.findProductByProductSeq(productSeq)
			.orElseThrow(ProductNotFoundException::new);

		product.setState(ProductActiveState.SOLDOUT.getState());
	}

	public void activeProductByProductSeq(Long productSeq) {
		Product product = productRepository.findProductByProductSeq(productSeq)
			.orElseThrow(ProductNotFoundException::new);

		product.setState(ProductActiveState.ACTIVE.getState());
	}
}
