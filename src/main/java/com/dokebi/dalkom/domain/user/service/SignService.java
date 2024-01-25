package com.dokebi.dalkom.domain.user.service;

import com.dokebi.dalkom.domain.user.exception.EmployeeNotFoundException;
import com.dokebi.dalkom.domain.user.exception.UserEmailAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNicknameAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.service.AdminService;
import com.dokebi.dalkom.domain.user.dto.LogInAdminRequest;
import com.dokebi.dalkom.domain.user.dto.LogInRequest;
import com.dokebi.dalkom.domain.user.dto.LogInResponse;
import com.dokebi.dalkom.domain.user.dto.SignUpRequest;
import com.dokebi.dalkom.domain.user.dto.SignUpResponse;
import com.dokebi.dalkom.domain.user.entity.Employee;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.LoginFailureException;
import com.dokebi.dalkom.domain.user.repository.EmployeeRepository;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignService {

	private final TokenService tokenService;
	private final RedisService redisService;
	private final AdminService adminService;
	private final UserRepository userRepository;
	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public LogInResponse signIn(LogInRequest req) {
		User user = userRepository.findByEmail(req.getEmail());
		validatePassword(req, user);
		String subject = createSubject(user);
		String accessToken = tokenService.createAccessToken(subject);
		String refreshToken = tokenService.createRefreshToken(subject);
		redisService.createValues(accessToken, refreshToken);
		return new LogInResponse(accessToken, refreshToken);
	}

	@Transactional(readOnly = true)
	public LogInResponse signInAdmin(LogInAdminRequest req) {
		Admin admin = adminService.readAdminByAdminId(req.getAdminId());
		if (admin == null)
			throw new LoginFailureException();
		validatePassword(req, admin);
		String subject = createSubject(admin);
		String accessToken = tokenService.createAccessToken(subject);
		String refreshToken = tokenService.createRefreshToken(subject);
		redisService.createValues(accessToken, refreshToken);
		return new LogInResponse(accessToken, refreshToken);
	}

	private String createSubject(User user) {
		return String.valueOf(user.getUserSeq());
	}

	private String createSubject(Admin admin) {
		return String.valueOf(admin.getAdminSeq()) + ",Admin";
	}

	private void validatePassword(LogInRequest req, User user) {
		if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
			throw new LoginFailureException();
		}
	}

	private void validatePassword(LogInAdminRequest req, Admin admin) {
		if (!passwordEncoder.matches(req.getPassword(), admin.getPassword())) {
			throw new LoginFailureException();
		}
	}

	@Transactional
	public SignUpResponse signUp(SignUpRequest req) {
		SignUpResponse signUpResponse = new SignUpResponse();

		// 임직원 테이블에 입력한 정보가 있는지 확인
		if (checkEmployee(req)) {
			// 이메일, 닉네임 중복성 검사
			validateSignUpInfo(req);

			// 임직원의 입사 기준에 따라 마일리지 세팅하는 로직 필요
			req.setMileage(0);

			// 비밀번호 암호화
			String password = passwordEncoder.encode(req.getPassword());
			req.setPassword(password);

			// 회원 정보 저장
			userRepository.save(SignUpRequest.toEntity(req));
			signUpResponse.setEmpId(req.getEmpId());
			signUpResponse.setEmail(req.getEmail());
			signUpResponse.setMessage("회원가입 성공");
		} else {
			signUpResponse.setMessage("임직원 데이터가 존재하지 않음");
		}

		return signUpResponse;
	}

	//임직원 데이터 조회, 일치여부 확인
	public boolean checkEmployee(SignUpRequest req) {

		Employee employee = employeeRepository.findAllByEmpId(req.getEmpId());
		if (employee != null &&
				employee.getName().equals(req.getName()) &&
				employee.getEmail().equals(req.getEmail()) &&
				employee.getJoinedAt().equals(req.getJoinedAt())) {
			return true;
		} else {
			throw new EmployeeNotFoundException();
		}
	}

	// 이메일, 닉네임 중복성 검사
	private void validateSignUpInfo(SignUpRequest req) {
		if (userRepository.existsByEmail(req.getEmail())) {
			throw new UserEmailAlreadyExistsException(req.getEmail());
		}
		if (userRepository.existsByNickname(req.getNickname())) {
			throw new UserNicknameAlreadyExistsException(req.getNickname());
		}
	}

}
