package com.dokebi.dalkom.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.service.AdminService;
import com.dokebi.dalkom.domain.user.dto.LogInAdminRequest;
import com.dokebi.dalkom.domain.user.dto.LogInAdminResponse;
import com.dokebi.dalkom.domain.user.dto.LogInRequest;
import com.dokebi.dalkom.domain.user.dto.LogInUserResponse;
import com.dokebi.dalkom.domain.user.dto.SignUpRequest;
import com.dokebi.dalkom.domain.user.dto.SignUpResponse;
import com.dokebi.dalkom.domain.user.entity.Employee;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.EmployeeNotFoundException;
import com.dokebi.dalkom.domain.user.exception.LoginFailureException;
import com.dokebi.dalkom.domain.user.exception.UserEmailAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNicknameAlreadyExistsException;
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
	public LogInUserResponse signIn(LogInRequest request) {
		User user = userRepository.findByEmail(request.getEmail());
		validatePassword(request, user);
		Integer mileage = user.getMileage();
		String subject = createSubject(user);
		String accessToken = tokenService.createAccessToken(subject);
		String refreshToken = tokenService.createRefreshToken(subject);
		// redis에 accessToken : refreshToken 형태로 저장된다.
		redisService.createValues(accessToken, refreshToken);
		// 로그인 시 refreshToken는 반환되지 않는다.
		return new LogInUserResponse(accessToken, mileage);
	}

	@Transactional(readOnly = true)
	public LogInAdminResponse signInAdmin(LogInAdminRequest request) {
		Admin admin = adminService.readAdminByAdminId(request.getAdminId());
		if (admin == null)
			throw new LoginFailureException();
		validatePassword(request, admin);
		String role = admin.getRole();
		String subject = createSubject(admin);
		String accessToken = tokenService.createAccessToken(subject);
		String refreshToken = tokenService.createRefreshToken(subject);
		// redis에 accessToken : refreshToken 형태로 저장된다.
		redisService.createValues(accessToken, refreshToken);
		// 로그인 시 refreshToken는 반환되지 않는다.
		return new LogInAdminResponse(accessToken, role);
	}

	private String createSubject(User user) {
		return String.valueOf(user.getUserSeq());
	}

	private String createSubject(Admin admin) {
		return admin.getAdminSeq() + ",Admin";
	}

	private void validatePassword(LogInRequest request, User user) {
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new LoginFailureException();
		}
	}

	private void validatePassword(LogInAdminRequest request, Admin admin) {
		if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
			throw new LoginFailureException();
		}
	}

	@Transactional
	public SignUpResponse signUp(SignUpRequest request) {
		SignUpResponse signUpResponse = new SignUpResponse();

		// 임직원 테이블에 입력한 정보가 있는지 확인
		if (checkEmployee(request)) {
			// 이메일, 닉네임 중복성 검사
			validateSignUpInfo(request);

			// 임직원의 입사 기준에 따라 마일리지 세팅하는 로직 필요
			request.setMileage(0);

			// 비밀번호 암호화
			String password = passwordEncoder.encode(request.getPassword());
			request.setPassword(password);

			// 회원 정보 저장
			userRepository.save(SignUpRequest.toEntity(request));
			signUpResponse.setEmpId(request.getEmpId());
			signUpResponse.setEmail(request.getEmail());
			signUpResponse.setMessage("회원가입 성공");
		} else {
			signUpResponse.setMessage("임직원 데이터가 존재하지 않음");
		}

		return signUpResponse;
	}

	//임직원 데이터 조회, 일치여부 확인
	private boolean checkEmployee(SignUpRequest request) {

		Employee employee = employeeRepository.findAllByEmpId(request.getEmpId());
		if (employee != null &&
			employee.getName().equals(request.getName()) &&
			employee.getEmail().equals(request.getEmail()) &&
			employee.getJoinedAt().equals(request.getJoinedAt())) {
			return true;
		} else {
			throw new EmployeeNotFoundException();
		}
	}

	// 이메일, 닉네임 중복성 검사
	private void validateSignUpInfo(SignUpRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UserEmailAlreadyExistsException(request.getEmail());
		}
		if (userRepository.existsByNickname(request.getNickname())) {
			throw new UserNicknameAlreadyExistsException(request.getNickname());
		}
	}

}
