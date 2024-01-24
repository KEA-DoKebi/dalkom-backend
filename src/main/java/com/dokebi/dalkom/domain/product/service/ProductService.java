package com.dokebi.dalkom.domain.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDTO;
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
@Transactional
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final ProductStockService productStockService;
	private final CategoryService categoryService;
	private final ProductOptionService productOptionService;

	@Transactional
	public void createProduct(ProductCreateRequest request) {
		Category category = categoryService.readCategoryBySeq(request.getCategorySeq());

		Product newProduct = new Product(category, request.getName(), request.getPrice(), request.getInfo(),
			request.getImageUrl(), request.getCompany(), request.getState());

		productRepository.save(newProduct);

		// 초기 재고 등록
		for (OptionAmountDto optionAmountDTO : request.getPrdtOptionList()) {
			ProductOption option = productOptionService.readProductOptionByPrdtOptionSeq(
				optionAmountDTO.getPrdtOptionSeq());

			ProductStock initialStock = new ProductStock(newProduct, option, optionAmountDTO.getAmount());

			productStockService.createStock(initialStock);
		}
	}

	public Product readProductByProductSeq(Long productSeq) {
		return productRepository.findByProductSeq(productSeq).orElseThrow(ProductNotFoundException::new);
	}

	// Product 001 - 상위 카테고리로 상품 리스트 조회
	public Page<ProductByCategoryResponse> readProductListByCategory(Long categorySeq, Pageable pageable) {
		Page<ProductByCategoryResponse> productList = productRepository.findProductListByCategory(categorySeq,
			pageable);

		if (productList == null || productList.isEmpty()) {
			throw new ProductNotFoundException();
		}

		return productList;
	}

	// Product 004 - 하위 카테고리로 상품 리스트 조회
	public Page<ProductByCategoryDetailResponse> readProductListByDetailCategory(Long categorySeq, Pageable pageable) {
		Page<ProductByCategoryDetailResponse> productList = productRepository.findProductListByDetailCategory(
			categorySeq, pageable);

		if (productList == null || productList.isEmpty()) {
			throw new ProductNotFoundException();
		}

		return productList;
	}

	public ReadProductDetailResponse readProduct(Long productSeq) {
		ReadProductDetailDTO productDetailDTO = productRepository.findProductDetailBySeq(productSeq);

		List<StockListDto> stockList = productStockService.readStockListDtoByProductSeq(productSeq);
		List<OptionListDto> optionList = productOptionService.readOptionListDtoByProductSeq(productSeq);
		List<String> productImageUrlList = productRepository.findProductImageBySeq(productSeq);

		if (stockList == null || optionList == null || productImageUrlList == null || stockList.isEmpty()
			|| optionList.isEmpty() || productImageUrlList.isEmpty()) {
			throw new ProductNotFoundException();
		}

		return new ReadProductDetailResponse(productDetailDTO, optionList, stockList, productImageUrlList);
	}

	public Page<ReadProductResponse> readAdminProductList(Pageable pageable) {
		return productRepository.findAdminProductList(pageable);
	}

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
}
