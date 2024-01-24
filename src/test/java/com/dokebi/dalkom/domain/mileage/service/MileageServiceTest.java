package com.dokebi.dalkom.domain.mileage.service;

import static com.dokebi.dalkom.domain.mileage.factory.MileageHistoryResponseFactory.*;
import static com.dokebi.dalkom.domain.user.factory.UserFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.domain.mileage.dto.MileageHistoryResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;
import com.dokebi.dalkom.domain.mileage.repository.MileageHistoryRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

class MileageServiceTest {

	@InjectMocks
	private MileageService mileageService;

	@Mock
	private MileageHistoryRepository mileageHistoryRepository;

	@Mock
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("유저 별 보유 마일리지 조회 서비스 ")
	void readMileageByUserSeqTest() {
		// Mock 데이터 설정
		Long userSeq = 1L;
		when(mileageHistoryRepository.findMileageByUserSeq(userSeq))
			.thenReturn(Arrays.asList(100, 200, 300));

		// 특정 사용자의 마일리지 조회
		Integer result = mileageService.readMileageByUserSeq(userSeq);

		// 결과 확인
		assertEquals(100, result); // 가장 최근 마일리지인 100이 반환되어야 함
	}

	@Test
	@DisplayName("유저 별 마일리지 히스토리 조회 서비스")
	void readMileageHistoryByUserSeqTest() {
		// Mock 데이터 설정
		Long userSeq = 1L;
		User user = createMockUser();
		Pageable pageable = PageRequest.of(0, 2);

		List<MileageHistoryResponse> mileageHistoryList = new ArrayList<>();
		mileageHistoryList.add(createMileageHistoryResponse(user));
		mileageHistoryList.add(createMileageHistoryResponse(user));

		Page<MileageHistoryResponse> mileageHistoryPage = new PageImpl<>(mileageHistoryList, pageable,
			mileageHistoryList.size());

		// Mock repository의 메서드가 null을 반환하지 않도록 설정
		when(mileageService.readMileageHistoryByUserSeq(eq(userSeq), eq(pageable))).thenReturn(mileageHistoryPage);

		Page<MileageHistoryResponse> result = mileageService.readMileageHistoryByUserSeq(userSeq, pageable);

		// then
		assertNotNull(result);
		assertEquals(mileageHistoryList.size(), result.toList().size());
	}

	@Test
	@DisplayName("관리자가 충전 승인하는경우 마일리지 히스토리 내역에 추가 ")
	void createMileageHistoryAndUpdateUserTest() {
		Long userSeq = 1L;
		Integer amount = 1000;
		String type = "2";

		User user = createMockUser(); // 적절한 값으로 초기화
		user.setMileage(500); // 적절한 초기 마일리지 값으로 설정

		MileageHistory savedMileageHistory = new MileageHistory(amount, user.getMileage() + amount, type, user);

		// Mocking
		when(userService.readUserByUserSeq(userSeq)).thenReturn(user);
		when(mileageHistoryRepository.save(any(MileageHistory.class))).thenReturn(savedMileageHistory);

		// When
		mileageService.createMileageHistoryAndUpdateUser(userSeq, amount, type);

		// Then
		verify(userService).readUserByUserSeq(userSeq);
		verify(mileageHistoryRepository).save(any(MileageHistory.class));
		verify(userService).updateUser(user); // updateUser 메서드가 호출되었는지 확인

		// 추가적으로 다른 상호작용이 없도록 확인
		verifyNoMoreInteractions(userService, mileageHistoryRepository);
	}

}

