package com.dokebi.dalkom.domain.mileage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.mileage.dto.MileageHistoryResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;
import com.dokebi.dalkom.domain.mileage.repository.MileageHistoryRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageService {
	private final MileageHistoryRepository mileageHistoryRepository;
	private final UserService userService;

	// 유저 별 보유 마일리지 조회 서비스
	public Integer readMileageByUserSeq(Long userSeq) {
		// 제일 위에 있는 내역 불러오기
		List<Integer> mileageList = mileageHistoryRepository.findMileageByUserSeq(userSeq);
		if (!mileageList.isEmpty()) {
			return mileageList.get(0);
		} else {
			return 0;
		}
	}

	// 유저별 마일리지 내역 조회 서비스
	public Page<MileageHistoryResponse> readMileageHistoryByUserSeq(Long userSeq, Pageable pageable) {

		return mileageHistoryRepository.findMileageHistoryListByUserSeq(userSeq, pageable);
	}

	// 관리자가 충전을 승인하는 경우 마일리지 내역을 추가하는 서비스
	public void createMileageHistory(User user, Integer amount, Integer totalMileage, String type) {
		MileageHistory mileageHistory = new MileageHistory(amount, totalMileage, type, user);
		mileageHistoryRepository.save(mileageHistory);
	}
}
