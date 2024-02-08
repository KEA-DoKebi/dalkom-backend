package com.dokebi.dalkom.domain.user.service;

import static com.dokebi.dalkom.domain.user.factory.UserFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dokebi.dalkom.domain.user.dto.ReadUserSelfDetailResponse;
import com.dokebi.dalkom.domain.user.dto.UserListResponse;
import com.dokebi.dalkom.domain.user.dto.UserUpdateRequest;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.UserNicknameAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
<<<<<<< HEAD
class UserServiceTest {
=======
public class UserServiceTest {
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043

	@Mock
	private UserRepository userRepository;
	@Mock
	private Pageable pageable;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	private User mockUser;
	private User mockUserWithInsufficientMileage;

	@BeforeEach
	void setUp() {
		mockUser = createMockUser();
		mockUserWithInsufficientMileage = createMockUserWithInsufficientMileage();
	}

	@Test
	@DisplayName("사용자 정보 업데이트 - 성공")
	void updateUserByUserSeq_Success() {
		UserUpdateRequest request = new UserUpdateRequest("newPassword", "newNickname", "newAddress");

		when(userRepository.findByUserSeq(anyLong())).thenReturn(Optional.of(mockUser));
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

		userService.updateUserByUserSeq(1L, request);

		verify(userRepository).findByUserSeq(1L);
		verify(passwordEncoder).encode("newPassword");
	}

	@Test
	@DisplayName("사용자 정보 업데이트 - 닉네임이 이미 존재할 경우 예외 발생")
	void updateUserByUserSeq_NicknameAlreadyExists_ThrowsException() {
		UserUpdateRequest request = new UserUpdateRequest("newPassword", "existingNickname", "newAddress");

		when(userRepository.findByUserSeq(anyLong())).thenReturn(Optional.of(mockUser));
		when(userRepository.existsByNickname(anyString())).thenReturn(true);

		assertThrows(UserNicknameAlreadyExistsException.class, () -> userService.updateUserByUserSeq(1L, request));

		verify(userRepository).findByUserSeq(1L);
		verify(userRepository).existsByNickname("existingNickname");
	}

	@Test
	@DisplayName("사용자 정보 업데이트 - 사용자를 찾을 수 없을 경우 예외 발생")
	void updateUserByUserSeq_UserNotFound_ThrowsException() {
		UserUpdateRequest request = new UserUpdateRequest("newPassword", "newNickname", "newAddress");

		when(userRepository.findByUserSeq(anyLong())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.updateUserByUserSeq(1L, request));

		verify(userRepository).findByUserSeq(1L);
	}

	@Test
	@DisplayName("사용자 목록 조회")
	void readUserList() {
		Page<User> userPage = new PageImpl<>(List.of(mockUser));
		when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

		Page<UserListResponse> response = userService.readUserList(pageable);

		assertNotNull(response);
		assertEquals(1, response.getContent().size());
		assertEquals("chulsu", response.getContent().get(0).getNickname());

		verify(userRepository).findAll(pageable);
	}

	@Test
	@DisplayName("사용자 상세 조회 - 성공")
	void readUserByUserSeq_Success() {
		when(userRepository.findByUserSeq(anyLong())).thenReturn(Optional.of(mockUser));

		User result = userService.readUserByUserSeq(1L);

		assertNotNull(result);
		assertEquals("chulsu", result.getNickname());

		verify(userRepository).findByUserSeq(1L);
	}

	@Test
	@DisplayName("사용자 상세 조회 - 사용자를 찾을 수 없음")
	void readUserByUserSeq_UserNotFound_ThrowsException() {
		when(userRepository.findByUserSeq(anyLong())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.readUserByUserSeq(1L));

		verify(userRepository).findByUserSeq(1L);
	}

	@Test
	@DisplayName("사용자 검색 조회 - 이메일로 조회")
	void readUserListSearchByEmail() {
		UserListResponse mockUserResponse = UserListResponse.toDto(mockUser);
		UserListResponse mockUserWithInsufficientMileageResponse = UserListResponse
			.toDto(mockUserWithInsufficientMileage);

		// 이메일 검색에 대한 응답을 생성합니다. 이 경우, 단일 사용자 mockUserResponse를 포함하는 페이지입니다.
		List<UserListResponse> responses = List.of(mockUserResponse);
		Page<UserListResponse> responsePage = new PageImpl<>(responses, pageable, responses.size());
		when(userRepository.findUserListByEmail(anyString(), any(Pageable.class))).thenReturn(responsePage);

		// UserService의 readUserListSearch 메소드를 호출합니다.
		Page<UserListResponse> resultPage = userService.readUserListSearch("chulsu@example.com", null, pageable);

		// 응답을 검증합니다.
		assertNotNull(resultPage);
		assertEquals(1, resultPage.getContent().size()); // 기대하는 결과는 한 명의 사용자입니다.
		assertEquals("chulsu@example.com", resultPage.getContent().get(0).getEmail());
<<<<<<< HEAD
=======
		assertEquals(30000, resultPage.getContent().get(0).getMileage());
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043

		// UserRepository의 findUserListByEmail 메소드가 예상대로 호출되었는지 확인합니다.
		verify(userRepository).findUserListByEmail(eq("chulsu@example.com"), any(Pageable.class));
	}

	@Test
	@DisplayName("사용자 검색 조회 - 닉네임으로 조회")
	void readUserListSearchByNickname() {
		UserListResponse mockUserResponse = UserListResponse.toDto(mockUser);

		List<UserListResponse> responses = List.of(mockUserResponse);
		Page<UserListResponse> responsePage = new PageImpl<>(responses, pageable, responses.size());
		when(userRepository.findUserListByNickname(anyString(), any(Pageable.class))).thenReturn(responsePage);

		Page<UserListResponse> resultPage = userService.readUserListSearch(null, "chulsu", pageable);

		assertNotNull(resultPage);
		assertEquals(1, resultPage.getContent().size());
		assertEquals("chulsu", resultPage.getContent().get(0).getNickname());
<<<<<<< HEAD
=======
		assertEquals(30000, resultPage.getContent().get(0).getMileage());
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043

		verify(userRepository).findUserListByNickname(eq("chulsu"), any(Pageable.class));
	}

	@Test
	@DisplayName("자신의 상세 정보 조회")
	void readUserSelfDetail() {
		when(userRepository.findByUserSeq(anyLong())).thenReturn(Optional.of(mockUser));

		ReadUserSelfDetailResponse response = userService.readUserSelfDetail(1L);

		assertNotNull(response);
		assertEquals("chulsu", response.getNickname());
		assertEquals("chulsu@example.com", response.getEmail());

		verify(userRepository).findByUserSeq(1L);
	}
}
