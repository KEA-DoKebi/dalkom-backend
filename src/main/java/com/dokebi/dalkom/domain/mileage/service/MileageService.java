package com.dokebi.dalkom.domain.mileage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.mileage.dto.MileageHistoryDto;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;
import com.dokebi.dalkom.domain.mileage.repository.MileageHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageService {
	private final MileageHistoryRepository mileageHistoryRepository;

	public Integer readMileageByUserSeq(Long userSeq) {
		log.info(String.valueOf(mileageHistoryRepository.findMileageByUserSeq(userSeq)));
		
		return  mileageHistoryRepository.findMileageByUserSeq(userSeq);
	}

	public List<MileageHistoryDto> readMileageHistoryByUserSeq(Long userSeq) {
		List<MileageHistory> mileageHistories = mileageHistoryRepository.findMileageHistoriesByUserSeq(userSeq);

		return mileageHistories.stream()
			.map(history -> new MileageHistoryDto(
				history.getType(),
				history.getCreatedAt(),
				history.getBalance(),
				history.getAmount()))
			.collect(Collectors.toList());
	}
}
