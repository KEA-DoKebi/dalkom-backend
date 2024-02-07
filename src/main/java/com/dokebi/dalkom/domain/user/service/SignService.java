package com.dokebi.dalkom.domain.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.service.AdminService;
import com.dokebi.dalkom.domain.jira.exception.MissingJiraRequestHeaderException;
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
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;
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
	public LogInUserResponse signInUser(LogInRequest request) {
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);
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

	@Transactional
	public Response signOut(HttpServletRequest request) {
		String token = (request.getHeader("AccessToken"));
		if (token == null || token.isEmpty()) {
			throw new MissingJiraRequestHeaderException();
		}
		redisService.deleteValues(token);
		return Response.success();
	}

	private String createSubject(User user) {
		return String.valueOf(user.getUserSeq());
	}

	private String createSubject(Admin admin) {
		return admin.getAdminSeq() + ",Admin";
	}

	private void validatePassword(LogInRequest request, User user) {
		try {
			if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
				throw new LoginFailureException();
			}
		} catch (IllegalArgumentException e) {
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

			//마일리지 추가
			request.setMileage(calculateMileage(request.getJoinedAt()));

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

	public Integer calculateMileage(LocalDate joinedAt) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDate startOfYear = LocalDate.of(currentDateTime.getYear(), 1, 1);

		// 15일 구분용
		LocalDate midDate = LocalDate.of(joinedAt.getYear(), joinedAt.getMonth(), 15);
		if (joinedAt.isBefore(midDate)) {
			joinedAt = joinedAt.withDayOfMonth(1);
		} else {
			joinedAt = joinedAt.plusMonths(1).withDayOfMonth(1);
		}

		int mileagePerMonth = 100000; //10만
		int mileagePerYear = mileagePerMonth * 12;
		int monthWorked;
		int totalMileage;

		if (joinedAt.isBefore(startOfYear)) {
			monthWorked = Math.toIntExact(ChronoUnit.MONTHS.between(joinedAt, startOfYear));
			totalMileage = monthWorked * mileagePerMonth + mileagePerYear;
			return Math.min(totalMileage, mileagePerYear);

		} else {
			monthWorked = Math.toIntExact(ChronoUnit.MONTHS.between(startOfYear, joinedAt));
			return monthWorked * mileagePerMonth;
		}
	}

	// 임직원 데이터 조회, 일치여부 확인
	private boolean checkEmployee(SignUpRequest request) {
		Employee employee = employeeRepository.findByEmpId(request.getEmpId())
			.orElseThrow(EmployeeNotFoundException::new);
		if (employee.getName().equals(request.getName()) &&
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
