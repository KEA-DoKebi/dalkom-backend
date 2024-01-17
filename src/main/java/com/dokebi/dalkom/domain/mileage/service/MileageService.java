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

	// 유저별 보유 마일리지  조회 서비스
	public Integer readMileageByUserSeq(Long userSeq) {
		List<Integer> mileageList = mileageHistoryRepository.findMileageByUserSeq(userSeq);
		if (!mileageList.isEmpty()) {
			log.info("Mileage found: " + mileageList.get(0));
			return mileageList.get(0);
		} else {
			log.info("No mileage found for user with userSeq: " + userSeq);
			return 0;
		}
	}
	// 유저별 마일리지 내역 조회 서비스
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
