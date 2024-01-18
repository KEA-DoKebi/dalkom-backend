package com.dokebi.dalkom.domain.mileage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.mileage.dto.MileageHistoryDto;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;
import com.dokebi.dalkom.domain.mileage.repository.MileageHistoryRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageService {
	private final MileageHistoryRepository mileageHistoryRepository;
	private final UserRepository userRepository;

	// 유저 별 보유 마일리지 조회 서비스
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

	// 유저 별 마일리지 내역 조회 서비스
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

	// 관리자가 충전을 승인하는 경우 마일리지 내역을 추가하는 서비스
	public void createMileageHistoryAndUpdateUser(Long userSeq, Integer amount, String type) {
		User user = userRepository.findById(userSeq).orElse(null);
		if (user != null) {
			MileageHistory mileageHistory = new MileageHistory(amount, user.getMileage() + amount, type, user);
			mileageHistoryRepository.save(mileageHistory);

			// 사용자의 마일리지 업데이트
			user.setMileage(user.getMileage() + amount);
			userRepository.save(user);

			// 여기서 MileageHistoryDto를 생성하거나 반환할 필요가 있다면 추가 작업을 진행하세요.
			log.info("Mileage history created and user mileage updated successfully.");
		} else {
			log.error("User with userSeq={} not found.", userSeq);
			// 사용자를 찾을 수 없는 경우 예외 처리 또는 적절한 로깅을 수행하세요.
		}
		// 필요에 따라 MileageHistoryDto를 반환하거나 그냥 void로 메서드를 정의하세요.
	}
}
