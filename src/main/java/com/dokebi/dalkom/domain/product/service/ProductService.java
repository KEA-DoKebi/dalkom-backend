package com.dokebi.dalkom.domain.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.repository.CategoryRepository;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.repository.ProductOptionRepository;
import com.dokebi.dalkom.domain.product.dto.OptionAmountDTO;
import com.dokebi.dalkom.domain.product.dto.OptionListDTO;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDTO;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.dto.StockListDTO;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
import com.dokebi.dalkom.domain.stock.entity.ProductStock;
import com.dokebi.dalkom.domain.stock.repository.ProductStockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final ProductStockRepository productStockRepository;
	private final CategoryRepository categoryRepository;
	private final ProductOptionRepository productOptionRepository;

	@Transactional
	public void createProduct(ProductCreateRequest request) {
		Category category = categoryRepository.getById(request.getCategorySeq());
		Product newProduct = new Product(category, request.getName(), request.getPrice(), request.getInfo(),
			request.getImageUrl(), request.getCompany(), request.getState());
		productRepository.save(newProduct);

		// 초기 재고 등록
		for (OptionAmountDTO optionAmountDTO : request.getPrdtOptionList()) {
			ProductOption option = productOptionRepository.getById(optionAmountDTO.getPrdtOptionSeq());
			ProductStock initialStock = new ProductStock(newProduct, option, optionAmountDTO.getAmount());

			productStockRepository.save(initialStock);
		}
	}

	@Transactional
	public List<ProductByCategoryResponse> getProductsByCategory(Long categorySeq) {
		return productRepository.getProductsByCategory(categorySeq);
	}

	//쿼리 결과를 조합해서 return하는 메서드
	public ReadProductDetailResponse readProduct(Long productSeq) {
		ReadProductDetailDTO productDetailDTO = productRepository.getProductDetailBySeq(productSeq);
		List<StockListDTO> stockList = productRepository.getStockListBySeq(productSeq);
		List<OptionListDTO> optionList = productRepository.getOptionListBySeq(productSeq);
		List<String> productImageUrlList = productRepository.getProductImageBySeq(productSeq);
		return new ReadProductDetailResponse(productDetailDTO, optionList, stockList, productImageUrlList);
	}
}
