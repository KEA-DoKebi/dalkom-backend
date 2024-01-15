package com.dokebi.dalkom.domain.user.service;

//import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.user.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignService {

	@Transactional
	//생성일 삭제일 추가
	public void signUp(SignUpRequest req) {
		// validateSignUpInfo(req);
		// User user = userRepository.save(SignUpRequest.toEntity(req, passwordEncoder));
	}
}
