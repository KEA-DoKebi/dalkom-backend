package com.dokebi.dalkom.domain.stock.service;

import java.util.List;
import java.util.Objects;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicnumber.ProductActiveState;
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

	/** amount = DB에 저장할 값, amountChanged = 변화량 **/
	@Transactional
	public void updateStockByStockSeq(Long stockSeq, Integer amount) {
		ProductStock stock = stockRepository.findById(stockSeq).orElseThrow(ProductStockNotFoundException::new);
		Long productSeq = stock.getProduct().getProductSeq();

		if (amount < 0) {
			throw new InvalidAmountException();
		}

		int amountChanged = amount - stock.getAmount();
		stock.setAmount(amount);

		ProductStockHistory stockHistory = new ProductStockHistory(stock, amount, amountChanged);

		stockHistoryRepository.save(stockHistory);

		// 재고 변화 결과에 따라 해당 상품의 판매 상태 변경
		if (checkProductStock(stock.getPrdtStockSeq())) {
			productService.soldoutProductByProductSeq(productSeq);
		} else if (Objects.equals(productService.checkProductActiveState(productSeq), ProductActiveState.SOLDOUT)) {
			productService.activeProductByProductSeq(productSeq);
		}
	}

	/** amount = DB에 저장할 값, amountChanged = 변화량 **/
	@Transactional
	public void updateStockByProductSeqAndOptionSeq(Long productSeq, Long prdtOptionSeq, Integer amountChanged) {
		ProductStock stock = stockRepository.findPrdtStockByPrdtSeqAndPrdtOptionSeq(productSeq, prdtOptionSeq)
			.orElseThrow(ProductStockNotFoundException::new);

		Integer amount = stock.getAmount() - amountChanged;
		if (amount < 0) {
			throw new NotEnoughStockException();
		}

		stock.setAmount(amount);

		ProductStockHistory stockHistory = new ProductStockHistory(stock, amount, amountChanged);

		stockHistoryRepository.save(stockHistory);

		// 재고 변화 결과에 따라 해당 상품의 판매 상태 변경
		if (checkProductStock(stock.getPrdtStockSeq())) {
			productService.soldoutProductByProductSeq(productSeq);
		} else if (Objects.equals(productService.checkProductActiveState(productSeq), ProductActiveState.SOLDOUT)) {
			productService.activeProductByProductSeq(productSeq);
		}
	}

	@Transactional
	public void createStock(ProductStock stock) {
		stockRepository.save(stock);
	}

	// 주문 전 재고가 부족하진 않은지 확인하는 메서드
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

		// 해당 상품의 재고를 전부 조회한 뒤, 재고가 전부 0이면 true를 반환
		return productStockList.stream().allMatch(stock -> stock.getAmount() == 0);
	}
}
