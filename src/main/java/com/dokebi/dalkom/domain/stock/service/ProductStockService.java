package com.dokebi.dalkom.domain.stock.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.stock.dto.StockListDto;
import com.dokebi.dalkom.domain.stock.entity.ProductStock;
import com.dokebi.dalkom.domain.stock.entity.ProductStockHistory;
import com.dokebi.dalkom.domain.stock.exception.InvalidAmountException;
import com.dokebi.dalkom.domain.stock.exception.NotEnoughStockException;
import com.dokebi.dalkom.domain.stock.exception.ProductStockNotFoundException;
import com.dokebi.dalkom.domain.stock.repository.ProductStockHistoryRepository;
import com.dokebi.dalkom.domain.stock.repository.ProductStockRepository;

@Service
@Transactional(readOnly = true)
// @RequiredArgsConstructor
public class ProductStockService {
	private final ProductStockRepository stockRepository;
	private final ProductStockHistoryRepository stockHistoryRepository;
	private final ProductService productService;

	// 순환 참조 해결을 위해 생성자를 통해 @Lazy 사용
	public ProductStockService(ProductStockRepository stockRepository,
		ProductStockHistoryRepository stockHistoryRepository,
		@Lazy ProductService productService) {
		this.stockRepository = stockRepository;
		this.stockHistoryRepository = stockHistoryRepository;
		this.productService = productService;
	}

	@Transactional
	public void updateStock(Long stockSeq, Integer amount) {
		ProductStock stock = stockRepository.findById(stockSeq).orElseThrow(ProductStockNotFoundException::new);

		if (amount < 0) {
			throw new InvalidAmountException();
		}

		int amountChanged = amount - stock.getAmount();
		stock.setAmount(amount);

		ProductStockHistory stockHistory = new ProductStockHistory(stock, amount, amountChanged);

		stockHistoryRepository.save(stockHistory);
		if (checkProductStock(stockSeq)) {
			productService.deactiveProductByProductSeq(stock.getProduct().getProductSeq());
		}
	}

	@Transactional
	public void updateStock(Long productSeq, Long prdtOptionSeq, Integer amountChanged) {
		ProductStock stock = stockRepository.findPrdtStockByPrdtSeqAndPrdtOptionSeq(productSeq, prdtOptionSeq)
			.orElseThrow(ProductStockNotFoundException::new);

		Integer amount = stock.getAmount() - amountChanged;
		if (amount < 0) {
			throw new NotEnoughStockException();
		}

		stock.setAmount(amount);

		ProductStockHistory stockHistory = new ProductStockHistory(stock, amount, amountChanged);

		stockHistoryRepository.save(stockHistory);
		if (checkProductStock(stock.getPrdtStockSeq())) {
			productService.deactiveProductByProductSeq(productSeq);
		}
	}

	@Transactional
	public void createStock(ProductStock stock) {
		stockRepository.save(stock);
	}

	public void checkStock(Long productSeq, Long prdtOptionSeq, Integer amountChanged) {
		ProductStock stock = stockRepository.findPrdtStockByPrdtSeqAndPrdtOptionSeq(productSeq, prdtOptionSeq)
			.orElseThrow(ProductStockNotFoundException::new);

		if (stock.getAmount() - amountChanged < 0) {
			throw new NotEnoughStockException();
		}
	}

	public List<StockListDto> readStockListDtoByProductSeq(Long productSeq) {
		return stockRepository.findStockListDtoByProductSeq(productSeq);
	}

	@Transactional(readOnly = true)
	public ProductStock readStockByProductAndOptionSeq(Long productSeq, Long optionSeq) {
		return stockRepository.findPrdtStockByPrdtSeqAndPrdtOptionSeq(productSeq, optionSeq)
			.orElseThrow(ProductStockNotFoundException::new);

	}

	private boolean checkProductStock(Long stockSeq) {
		List<ProductStock> productStockList = stockRepository.findProductStockListByStockSeq(stockSeq);

		//해당 상품의 재고를 전부 조회한 뒤, 재고가 전부 0이면 true를 반환
		return productStockList.stream().allMatch(stock -> stock.getAmount() == 0);
	}
}
