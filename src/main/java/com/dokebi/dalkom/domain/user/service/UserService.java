package com.dokebi.dalkom.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.user.dto.ReadUserSelfDetailResponse;
import com.dokebi.dalkom.domain.user.dto.UserListResponse;
import com.dokebi.dalkom.domain.user.dto.UserUpdateRequest;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.UserNicknameAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;
import com.dokebi.dalkom.domain.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final RedisService redisService;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void updateUserByUserSeq(Long userSeq, UserUpdateRequest request,
		HttpServletRequest httpServletRequest) throws JsonProcessingException {
		User user = userRepository.findByUserSeq(userSeq).orElseThrow(UserNotFoundException::new);
		String key = (httpServletRequest.getHeader("AccessToken"));

		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		if (!user.getNickname().equals(request.getNickname())) {
			validateNickname(request.getNickname());
		}

		user.setNickname(request.getNickname());
		user.setAddress(request.getAddress());

		ReadUserSelfDetailResponse response = new ReadUserSelfDetailResponse(user.getEmail(), user.getName(),
			user.getNickname(), user.getAddress());

		redisService.createUserDetail(key + "self", response);
	}

	private void validateNickname(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			throw new UserNicknameAlreadyExistsException(nickname);
		}
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
		if (email != null) {
			return userRepository.findUserListByEmail(email, pageable);
		} else if (nickname != null) {
			return userRepository.findUserListByNickname(nickname, pageable);
		} else {
			// 다른 조건이 없는 경우 기본적인 조회 수행
			return userRepository.findAllUserList(pageable);
		}
	}

	public ReadUserSelfDetailResponse readUserSelfDetail(Long userSeq, HttpServletRequest request) throws
		JsonProcessingException {
		String key = (request.getHeader("AccessToken"));
		ReadUserSelfDetailResponse response = redisService.getUserDetail(key + "self");

		if (response == null) {
			System.out.println("redis에 내용물 없음");
			User user = userRepository.findByUserSeq(userSeq).orElseThrow(UserNotFoundException::new);
			response = new ReadUserSelfDetailResponse(user.getEmail(), user.getName(), user.getNickname(),
				user.getAddress());
			redisService.createUserDetail(key + "self", response);

			return response;
		} else {
			System.out.println("redis에 내용물 있었음");
			return response;
		}
	}
}
