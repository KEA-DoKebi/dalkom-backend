package com.dokebi.dalkom.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.user.dto.ReadUserSelfDetailResponse;
import com.dokebi.dalkom.domain.user.dto.UserListResponse;
import com.dokebi.dalkom.domain.user.dto.UserUpdateRequest;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.UserNicknameAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Response updateUser(Long userSeq, UserUpdateRequest request) {
		try {
			if (request.getPassword() != null && !request.getPassword().equals("")) {
				request.encodedPassword(passwordEncoder.encode(request.getPassword()));
				updateUserWithPassword(userSeq, request);
			} else
				updateUserWithoutPassword(userSeq, request);
			return Response.success();
		} catch (UserNicknameAlreadyExistsException e) {
			return Response.failure(0, e.getMessage());
		}
	}

	@Transactional
	public void updateUserWithPassword(Long userSeq, UserUpdateRequest request) {
		validateNickname(request.getNickname());
		userRepository.updateUserWithPassword(userSeq, request.getPassword(), request.getNickname(),
			request.getAddress());
	}

	@Transactional
	public void updateUserWithoutPassword(Long userSeq, UserUpdateRequest request) {
		validateNickname(request.getNickname());
		userRepository.updateUserWithoutPassword(userSeq, request.getNickname(), request.getAddress());
	}

	private void validateNickname(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			throw new UserNicknameAlreadyExistsException(nickname + "은 이미 사용중입니다.");
		}
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public Page<UserListResponse> readUserList(Pageable pageable) {
		Page<User> usersPage = userRepository.findAll(pageable);

		List<UserListResponse> dtoList = usersPage.getContent()
			.stream()
			.map(UserListResponse::toDto)
			.collect(Collectors.toList());

		return new PageImpl<>(dtoList, pageable, usersPage.getTotalElements());
	}

	public User readUserByUserSeq(Long userSeq) {
		return userRepository.findByUserSeq(userSeq).orElseThrow(UserNotFoundException::new);
	}

	public Page<UserListResponse> readUserListSearch(String email, String nickname, Pageable pageable) {
		Page<User> usersPage = userRepository.findUsersBySearch(email, nickname, pageable);

		List<UserListResponse> dtoList = usersPage.getContent()
			.stream()
			.map(UserListResponse::toDto)
			.collect(Collectors.toList());

		return new PageImpl<>(dtoList, pageable, usersPage.getTotalElements());
	}

	public ReadUserSelfDetailResponse readUserSelfDetail(Long userSeq) {
		User user = userRepository.findByUserSeq(userSeq).orElseThrow(UserNotFoundException::new);

		return new ReadUserSelfDetailResponse(user.getEmail(), user.getName(), user.getNickname(), user.getAddress());
	}
}
