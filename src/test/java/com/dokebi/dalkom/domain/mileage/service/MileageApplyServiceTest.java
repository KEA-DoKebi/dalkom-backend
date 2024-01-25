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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.domain.mileage.dto.MileageApplyRequest;
import com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.repository.MileageApplyRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class MileageApplyServiceTest {
	@InjectMocks
	private MileageApplyService mileageApplyService;

	@Mock
	private MileageApplyRepository mileageApplyRepository;

	@Mock
	private UserService userService;

	@Mock
	private MileageService mileageService;

	@Test
	@DisplayName("마일리지 신청 조회 테스트(관리자)")
	void readMileageAskTest() {
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		List<MileageApplyResponse> mileageApplyResponseList = new ArrayList<>();
		mileageApplyResponseList.add(createMileageAskResponse());
		mileageApplyResponseList.add(createMileageAskResponse());

		Page<MileageApplyResponse> mileageAskResponsePage = new PageImpl<>(mileageApplyResponseList, pageable,
			mileageApplyResponseList.size());
		when(mileageApplyService.readMileageAsk(pageable)).thenReturn(mileageAskResponsePage);

		Page<MileageApplyResponse> result = mileageApplyService.readMileageAsk(pageable);

		assertNotNull(result);
		assertEquals(mileageApplyResponseList.size(), result.toList().size());

	}

	@Test
	@DisplayName("마일리지 seq 조회 ")
	void readByMilgApplySeqTest() {
		Long mileageSeq = 1L;
		User user = createMockUser();
		MileageApply mileageApply = createMileageApplyResponse(user);
		given(mileageApplyRepository.findByMilgApplySeq(anyLong())).willReturn(Optional.of(mileageApply));

		MileageApply result = mileageApplyService.readByMilgApplySeq(mileageSeq);

		assertNotNull(result);
		assertEquals(mileageApply.getMilgApplySeq(), result.getMilgApplySeq());
	}

	@Test
	@DisplayName("마일리지 신청 상태 변경")
	void updateMileageAskState() {
		// given
		Long userSeq = 1L;
		MileageApplyRequest request = new MileageApplyRequest();
		request.setAmount(100);

		User mockUser = createMockUser();
		mockUser.setUserSeq(userSeq);

		MileageApply mileageApply = createMileageApplyResponse(mockUser);


		when(userService.readUserByUserSeq(eq(userSeq))).thenReturn(mockUser);
		when(mileageApplyRepository.save(any(MileageApply.class))).thenReturn(mileageApply);

		// when
		mileageApplyService.createMileageApply(userSeq, request);

		// then
		// Verify that the readUserByUserSeq method is called with the correct argument
		verify(userService, times(1)).readUserByUserSeq(eq(userSeq));

		// Verify that the save method is called with the correct argument
		verify(mileageApplyRepository, times(1)).save(any(MileageApply.class));
	}

	@Test
	@DisplayName("마일리지 신청하기")
	void createMileageApply() {
		// 테스트에 사용할 가상의 데이터 생성
		Long userSeq = 1L;
		int amount = 5000;
		MileageApplyRequest mileageApplyRequest = createMileageAskRequestFactory(amount);
		User user = createMockUser();
		// MileageApply mileageApply = createMileageApplyResponse(user);

		// Mock 객체에 대한 행동 설정
		when(userService.readUserByUserSeq(userSeq)).thenReturn(user);

		// 테스트 메서드 실행
		mileageApplyService.createMileageApply(userSeq, mileageApplyRequest);

		// Mock 객체가 특정 메서드들을 호출했는지 확인
		verify(userService, times(1)).readUserByUserSeq(userSeq);
		verify(mileageApplyRepository, times(1)).save(any(MileageApply.class));

	}

}
