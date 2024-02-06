package com.dokebi.dalkom.domain.mileage.service;

import static com.dokebi.dalkom.domain.mileage.factory.MileageApplyFactory.*;
import static com.dokebi.dalkom.domain.mileage.factory.MileageApplyRequestFactory.*;
import static com.dokebi.dalkom.domain.mileage.factory.MileageApplyResponseFactory.*;
import static com.dokebi.dalkom.domain.mileage.factory.MileageStateRequestFactory.*;
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
import com.dokebi.dalkom.domain.mileage.dto.MileageStateRequest;
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

	@Test
	@DisplayName("마일리지 신청 조회 테스트(관리자)")
	void readMileageApplyTest() {
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		List<MileageApplyResponse> mileageApplyResponseList = new ArrayList<>();
		mileageApplyResponseList.add(createMileageApplyResponse());
		mileageApplyResponseList.add(createMileageApplyResponse());

		Page<MileageApplyResponse> mileageAskResponsePage = new PageImpl<>(mileageApplyResponseList, pageable,
			mileageApplyResponseList.size());
		when(mileageApplyService.readMileageApply(pageable)).thenReturn(mileageAskResponsePage);

		Page<MileageApplyResponse> result = mileageApplyService.readMileageApply(pageable);

		assertNotNull(result);
		assertEquals(mileageApplyResponseList.size(), result.toList().size());

	}

	@Test
	@DisplayName("마일리지 신청 wait 상태 리스트 조회 ")
	void readMileageApplyWaitStateListTest() {
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		List<MileageApplyResponse> mileageApplyResponseList = new ArrayList<>();
		mileageApplyResponseList.add(createMileageApplyResponse());
		mileageApplyResponseList.add(createMileageApplyResponse());

		Page<MileageApplyResponse> mileageAskResponsePage = new PageImpl<>(mileageApplyResponseList, pageable,
			mileageApplyResponseList.size());
		when(mileageApplyService.readMileageApplyWaitStateList(pageable)).thenReturn(mileageAskResponsePage);

		Page<MileageApplyResponse> result = mileageApplyService.readMileageApplyWaitStateList(pageable);

		assertNotNull(result);
		assertEquals(mileageApplyResponseList.size(), result.toList().size());

	}

	@Test
	@DisplayName("마일리지 신청 조회 by 유저 seq")
	void readMileageApplyListByUserSeqTest() {
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목
		List<MileageApplyResponse> mileageApplyResponseList = new ArrayList<>();
		mileageApplyResponseList.add(createMileageApplyResponse());
		mileageApplyResponseList.add(createMileageApplyResponse());

		Page<MileageApplyResponse> mileageAskResponsePage = new PageImpl<>(mileageApplyResponseList, pageable,
			mileageApplyResponseList.size());
		when(mileageApplyService.readMileageApplyListByUserSeq(userSeq, pageable)).thenReturn(mileageAskResponsePage);

		Page<MileageApplyResponse> result = mileageApplyService.readMileageApplyListByUserSeq(userSeq, pageable);

		assertNotNull(result);
		assertEquals(mileageApplyResponseList.size(), result.toList().size());

	}

	@Test
	@DisplayName("마일리지신청 seq로 조회 ")
	void readByMilgApplySeqTest() {
		Long milgApplySeq = 1L;
		MileageApply mileageApply = createMileageApply();

		// Optional.ofNullable을 사용하여 Optional 객체로 감싸줍니다.
		given(mileageApplyRepository.findByMilgApplySeq(milgApplySeq)).willReturn(Optional.ofNullable(mileageApply));

		// When
		Optional<MileageApply> resultOptional = mileageApplyRepository.findByMilgApplySeq(milgApplySeq);

		// Then
		assertTrue(resultOptional.isPresent()); // Optional이 존재하는지 확인
		MileageApply result = resultOptional.get(); // Optional에서 값을 가져옴
		assertNotNull(result);
		assertEquals(mileageApply, result);
	}

	@Test
	@DisplayName("마일리지 신청 승인 및 사용자 마일리지 및 히스토리 업데이트")
	void updateMileageApplyApprovalTest() {
		// given
		Long milgApplySeq = 1L;
		String approvedState = "2";
		MileageStateRequest request = createMileageStateRequestFactory(approvedState);

		User mockUser = createMockUser();
		MileageApply mileageApply = createMileageApplyResponse(mockUser);

		// Optional.ofNullable 대신 Optional.of를 사용하여 mileageApply가 null이 아니라고 확실하게 표현합니다.
		given(mileageApplyRepository.findByMilgApplySeq(milgApplySeq)).willReturn(Optional.of(mileageApply));

		// when
		mileageApplyService.updateMileageApply(milgApplySeq, request);

		// then
		assertEquals(request.getApprovedState(), mileageApply.getApprovedState());

	}

	@Test
	@DisplayName("마일리지 신청하기")
	void createMileageApplyTest() {
		// 테스트에 사용할 가상의 데이터 생성
		Long userSeq = 1L;
		int amount = 5000;
		MileageApplyRequest mileageApplyRequest = createMileageApplyRequestFactory(amount);
		User user = createMockUser();
		MileageApply mileageApply = createMileageApplyResponse(user);

		// Mock 객체에 대한 행동 설정
		when(userService.readUserByUserSeq(userSeq)).thenReturn(user);
		when(mileageApplyRepository.save(any(MileageApply.class))).thenReturn(mileageApply); // 수정된 부분
		// 테스트 메서드 실행
		mileageApplyService.createMileageApply(userSeq, mileageApplyRequest);

		verify(mileageApplyRepository).save(any(MileageApply.class)); // Corrected

	}

	@Test
	@DisplayName("마일리지 히스토리 내역 전체 검색")
	void readMileageApplyHistoryListSearchTest() {

		Pageable pageable = Pageable.unpaged();
		when(mileageApplyRepository.findMileageHistoryApplyList(pageable)).thenReturn(Page.empty());

		Page<MileageApplyResponse> result =
			mileageApplyService.readMileageApplyHistoryListSearch(null, null, null, pageable);

		assertNotNull(result);
		verify(mileageApplyRepository).findMileageHistoryApplyList(pageable);

	}

	@Test
	@DisplayName("마일리지 히스토리 내역 이메일 검색")
	void readMileageApplyHistoryListSearchEmailTest() {
		String email = "email";
		Pageable pageable = Pageable.unpaged();
		when(mileageApplyRepository.findMileageApplyHistoryListByEmail(email, pageable)).thenReturn(Page.empty());

		Page<MileageApplyResponse> result =
			mileageApplyService.readMileageApplyHistoryListSearch(email, null, null, pageable);

		assertNotNull(result);
		verify(mileageApplyRepository).findMileageApplyHistoryListByEmail(email, pageable);

	}

	@Test
	@DisplayName("마일리지 히스토리 내역 닉네임 검색")
	void readMileageApplyHistoryListSearchNicknameTest() {

		String nickname = "nickname";
		Pageable pageable = Pageable.unpaged();
		when(mileageApplyRepository.findMileageApplyHistoryListByNickname(nickname, pageable)).thenReturn(Page.empty());

		Page<MileageApplyResponse> result =
			mileageApplyService.readMileageApplyHistoryListSearch(null, nickname, null, pageable);

		assertNotNull(result);
		verify(mileageApplyRepository).findMileageApplyHistoryListByNickname(nickname, pageable);

	}

	@Test
	@DisplayName("마일리지 히스토리 내역 네임 검색")
	void readMileageApplyHistoryListSearchNameTest() {

		String name = "name";
		Pageable pageable = Pageable.unpaged();
		when(mileageApplyRepository.findMileageApplyHistoryListByName(name, pageable)).thenReturn(Page.empty());

		Page<MileageApplyResponse> result =
			mileageApplyService.readMileageApplyHistoryListSearch(null, null, name, pageable);

		assertNotNull(result);
		verify(mileageApplyRepository).findMileageApplyHistoryListByName(name, pageable);

	}

	@Test
	@DisplayName("마일리지 대기 상태 내역 이메일 검색")
	void readMileageApplyWaitStateSearchEmailTest() {
		String email = "email";
		Pageable pageable = Pageable.unpaged();
		when(mileageApplyRepository.findAllMileageApplyWaitStateListByEmail(email, pageable)).thenReturn(Page.empty());

		Page<MileageApplyResponse> result =
			mileageApplyService.readMileageApplyWaitStateSearch(email, null, null, pageable);

		assertNotNull(result);
		verify(mileageApplyRepository).findAllMileageApplyWaitStateListByEmail(email, pageable);

	}

	@Test
	@DisplayName("마일리지 대기 상태 내역 닉네임 검색")
	void readMileageApplyWaitStateSearchNicknameTest() {
		String nickname = "nickname";
		Pageable pageable = Pageable.unpaged();
		when(mileageApplyRepository.findAllMileageApplyWaitStateListByNickname(nickname, pageable)).thenReturn(
			Page.empty());

		Page<MileageApplyResponse> result =
			mileageApplyService.readMileageApplyWaitStateSearch(null, nickname, null, pageable);

		assertNotNull(result);
		verify(mileageApplyRepository).findAllMileageApplyWaitStateListByNickname(nickname, pageable);

	}

	@Test
	@DisplayName("마일리지 대기 상태 내역 네임 검색")
	void readMileageApplyWaitStateSearchNameTest() {

		String name = "name";
		Pageable pageable = Pageable.unpaged();
		when(mileageApplyRepository.findAllMileageApplyWaitStateListByName(name, pageable)).thenReturn(Page.empty());

		Page<MileageApplyResponse> result =
			mileageApplyService.readMileageApplyWaitStateSearch(null, null, name, pageable);

		assertNotNull(result);
		verify(mileageApplyRepository).findAllMileageApplyWaitStateListByName(name, pageable);

	}

}
