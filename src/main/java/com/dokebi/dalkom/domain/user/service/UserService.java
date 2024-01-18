package com.dokebi.dalkom.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.response.Response;
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

	@Transactional
	public Response updateUser(Long userSeq, UserUpdateRequest req) {
		try {
			if (req.getPassword() != null)
				updateUserWithPassword(userSeq, req);
			else
				updateUserWithoutPassword(userSeq, req);
			return Response.success();
		} catch (UserNicknameAlreadyExistsException e) {
			// 닉네임 중복 예외 처리
			return Response.failure(0, e.getMessage());
		}
	}

	@Transactional
	public void updateUserWithPassword(Long userSeq, UserUpdateRequest req) {
		validateNickname(req.getNickname());
		userRepository.updateUserWithPassword(userSeq, req.getPassword(), req.getNickname(), req.getAddress());
	}

	@Transactional
	public void updateUserWithoutPassword(Long userSeq, UserUpdateRequest req) {
		validateNickname(req.getNickname());
		userRepository.updateUserWithoutPassword(userSeq, req.getNickname(), req.getAddress());
	}

	private void validateNickname(String nickname) {
		if (userRepository.existsByNickname(nickname)) {
			throw new UserNicknameAlreadyExistsException(nickname + "은 이미 사용중입니다.");
		}
	}

	public User readByUserSeq(Long userSeq){
		Optional<User> user = userRepository.findByUserSeq(userSeq);
		return user.orElseThrow(UserNotFoundException::new);

	}

	public void updateUser(User user){
		userRepository.save(user);
	}
}
