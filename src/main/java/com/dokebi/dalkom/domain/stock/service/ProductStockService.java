package com.dokebi.dalkom.domain.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.stock.entity.ProductStock;
import com.dokebi.dalkom.domain.stock.entity.ProductStockHistory;
import com.dokebi.dalkom.domain.stock.exception.InvalidAmountException;
import com.dokebi.dalkom.domain.stock.exception.NotEnoughStockException;
import com.dokebi.dalkom.domain.stock.repository.ProductStockHistoryRepository;
import com.dokebi.dalkom.domain.stock.repository.ProductStockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductStockService {
	private final ProductStockRepository stockRepository;
	private final ProductStockHistoryRepository stockHistoryRepository;

	@Transactional
	public void editStock(Long stockSeq, Integer amount) {
		ProductStock stock = stockRepository.findByPrdtStockSeq(stockSeq);
		if (amount < 0) {
			throw new InvalidAmountException();
		}
		int amountChanged = amount - stock.getAmount();
		stock.setAmount(amount);

		ProductStockHistory stockHistory = new ProductStockHistory(stock, amount, amountChanged);

		stockHistoryRepository.save(stockHistory);
	}

	@Transactional
	public void orderStock(Long productSeq, Long prdtOptionSeq, Integer amountChanged) {
		ProductStock stock = stockRepository.findPrdtStockByOptionSeq(productSeq, prdtOptionSeq);

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
		ProductStock stock = stockRepository.findPrdtStockByOptionSeq(productSeq, prdtOptionSeq);

		if (stock.getAmount() - amountChanged < 0) {
			throw new NotEnoughStockException();
		}
	}

}
