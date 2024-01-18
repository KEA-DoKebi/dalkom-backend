package com.dokebi.dalkom.domain.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.stock.entity.ProductStock;
import com.dokebi.dalkom.domain.stock.entity.ProductStockHistory;
import com.dokebi.dalkom.domain.stock.exception.InvalidAmountException;
import com.dokebi.dalkom.domain.stock.exception.NotEnoughStockException;
import com.dokebi.dalkom.domain.stock.exception.ProductStockNotFoundException;
import com.dokebi.dalkom.domain.stock.repository.ProductStockHistoryRepository;
import com.dokebi.dalkom.domain.stock.repository.ProductStockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductStockService {

	private final ProductStockRepository stockRepository;
	private final ProductStockHistoryRepository stockHistoryRepository;

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
	}

	@Transactional
	public void createStock(Long productSeq, Long prdtOptionSeq, Integer amountChanged) {

		ProductStock stock = stockRepository.findPrdtStockByOptionSeq(productSeq, prdtOptionSeq)
			.orElseThrow(ProductStockNotFoundException::new);

		Integer amount = stock.getAmount() - amountChanged;
		if (amount < 0) {
			throw new NotEnoughStockException();
		}
		stock.setAmount(amount);

		ProductStockHistory stockHistory = new ProductStockHistory(stock, amount, amountChanged);

		stockHistoryRepository.save(stockHistory);
	}

	@Transactional
	public void createStock(ProductStock stock) {

		stockRepository.save(stock);
	}

	@Transactional
	public void checkStock(Long productSeq, Long prdtOptionSeq, Integer amountChanged) {

		ProductStock stock = stockRepository.findPrdtStockByOptionSeq(productSeq, prdtOptionSeq)
			.orElseThrow(ProductStockNotFoundException::new);

		if (stock.getAmount() - amountChanged < 0) {
			throw new NotEnoughStockException();
		}
	}
}
