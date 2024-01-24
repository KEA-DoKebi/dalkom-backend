package com.dokebi.dalkom.domain.mileage.service;

import static com.dokebi.dalkom.domain.mileage.factory.MileageApplyResponseFactory.*;
import static com.dokebi.dalkom.domain.mileage.factory.MileageAskResponseFactory.*;
import static com.dokebi.dalkom.domain.mileage.factory.mileageAskRequestFactory.*;
import static com.dokebi.dalkom.domain.user.factory.UserFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.domain.mileage.dto.MileageAskRequest;
import com.dokebi.dalkom.domain.mileage.dto.MileageAskResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.repository.MileageAskRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)

public class MileageAskServiceTest {
	@InjectMocks
	private MileageAskService mileageAskService;

	@Mock
	private MileageAskRepository mileageAskRepository;

	@Mock
	private UserService userService;

	@Mock
	private MileageService mileageService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("마일리지 신청 조회 테스트(관리자)")
	void readMileageAskTest() {
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		List<MileageAskResponse> mileageAskResponseList = new ArrayList<>();
		mileageAskResponseList.add(createMileageAskResponse());
		mileageAskResponseList.add(createMileageAskResponse());

		Page<MileageAskResponse> mileageAskResponsePage = new PageImpl<>(mileageAskResponseList, pageable,
			mileageAskResponseList.size());
		when(mileageAskService.readMileageAsk(pageable)).thenReturn(mileageAskResponsePage);

		Page<MileageAskResponse> result = mileageAskService.readMileageAsk(pageable);

		assertNotNull(result);
		assertEquals(mileageAskResponseList.size(), result.toList().size());

	}

	@Test
	@DisplayName("마일리지 seq 조회 ")
	void readByMilgApplySeqTest() {
		Long mileageSeq = 1L;
		User user = createMockUser();
		MileageApply mileageApply = createMileageApplyResponse(user);
		given(mileageAskRepository.findByMilgApplySeq(anyLong())).willReturn(Optional.of(mileageApply));

		MileageApply result = mileageAskService.readByMilgApplySeq(mileageSeq);

		assertNotNull(result);
		assertEquals(mileageApply.getMilgApplySeq(), result.getMilgApplySeq());
	}

	@Test
	@DisplayName("마일리지 신청 상태 변경")
	void updateMileageAskState() {
		User user = createMockUser();
		MileageApply mileageApply = createMileageApplyResponse(user);
		Long milgApplySeq = 1L;

		when(mileageAskRepository.findByMilgApplySeq(milgApplySeq)).thenReturn(mileageApply);

		mileageAskService.updateMileageAskState(milgApplySeq);

		verify(mileageService, times(1)).createMileageHistoryAndUpdateUser(any(), any(), any());
		verify(mileageAskRepository, times(1)).save(mileageApply);
	}

	@Test
	@DisplayName("마일리지 신청하기")
	void createMileageAsk(){
		// 테스트에 사용할 가상의 데이터 생성
		Long userSeq = 1L;
		int amount = 5000;
		MileageAskRequest mileageAskRequest = createMileageAskRequestFactory(amount);
		User user = createMockUser();
		MileageApply mileageApply = createMileageApplyResponse(user);

		// Mock 객체에 대한 행동 설정
		when(userService.readUserByUserSeq(userSeq)).thenReturn(user);
		when(mileageAskRepository.save(any(MileageApply.class))).thenReturn(mileageApply);

		// 테스트 메서드 실행
		mileageAskService.createMileageAsk(userSeq, mileageAskRequest);

		// Mock 객체가 특정 메서드들을 호출했는지 확인
		verify(userService, times(1)).readUserByUserSeq(userSeq);
		verify(mileageAskRepository, times(1)).save(any(MileageApply.class));

	}

}
