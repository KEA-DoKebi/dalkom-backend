package com.dokebi.dalkom.domain.admin.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.admin.dto.AdminDashboardResponse;
import com.dokebi.dalkom.domain.admin.dto.CreateAdminRequest;
import com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.exception.AdminNotFoundException;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;
import com.dokebi.dalkom.domain.user.dto.SignUpRequest;
import com.dokebi.dalkom.domain.user.dto.SignUpResponse;
import com.dokebi.dalkom.domain.user.entity.Employee;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.EmployeeNotFoundException;
import com.dokebi.dalkom.domain.user.exception.UserEmailAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNicknameAlreadyExistsException;
import com.dokebi.dalkom.domain.user.repository.EmployeeRepository;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final EmployeeRepository employeeRepository;

	public Page<ReadAdminResponse> readAdminList(Pageable pageable) {
		return adminRepository.findAllAdminList(pageable);

	}

	@Transactional
	public void createAdmin(CreateAdminRequest request) {
		validateNickname(request.getNickname());

		// 비밀번호 암호화
		String password = passwordEncoder.encode(request.getPassword());
		request.setPassword(password);
		adminRepository.save(CreateAdminRequest.toEntity(request));

	}

	@Transactional
	public void createUser(SignUpRequest request) {
		SignUpResponse signUpResponse = new SignUpResponse();

		// 임직원 테이블에 입력한 정보가 있는지 확인
		if (checkEmployee(request)) {
			// 이메일, 닉네임 중복성 검사
			validateSignUpInfo(request);

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
	}

	@Transactional
	public void updateUser(Long userSeq) {
		Optional<User> optionalUser = userRepository.findById(userSeq);
		optionalUser.ifPresent(user -> {
			// 값이 존재할 때만 실행됨
			user.setState("N");
		});

	}

	public Admin readAdminByAdminSeq(Long adminSeq) {
		return adminRepository.findByAdminSeq(adminSeq).orElseThrow(AdminNotFoundException::new);
	}

	public Admin readAdminByAdminId(String adminId) {
		return adminRepository.findByAdminId(adminId).orElseThrow(AdminNotFoundException::new);
	}

	// 서비스
	public Page<ReadAdminResponse> readAdminListSearch(String name, String adminId, String depart, String nickname,
		Pageable pageable) {
		if (name != null) {
			return adminRepository.findAdminListByName(name, pageable);
		} else if (adminId != null) {
			return adminRepository.findAdminListByAdminId(adminId, pageable);
		} else if (depart != null) {
			return adminRepository.findAdminListByDepart(depart, pageable);
		} else if (nickname != null) {
			return adminRepository.findAdminListByNickname(nickname, pageable);
		} else {
			// 다른 조건이 없는 경우 기본적인 조회 수행
			return adminRepository.findAllAdminList(pageable);
		}
	}

	public AdminDashboardResponse readDashboard() {
		AdminDashboardResponse response = new AdminDashboardResponse();
		response.setTotalMileage(adminRepository.findTotalPrice());
		response.setTotalMonthlyMileage(adminRepository.findTotalMonthlyPrice());
		response.setTotalDailyMileage(adminRepository.findTotalDailyPrice());
		response.setMonthlyPriceList(adminRepository.findMonthlyPriceList());
		response.setMonthlyCategoryList(adminRepository.findMonthlyCategoryList());
		
		Pageable topFive = PageRequest.of(0, 5);
		response.setMonthlyProductList(adminRepository.findMonthlyProductList(topFive));
		return response;
	}

	private void validateNickname(String nickname) {
		if (adminRepository.existsByNickname(nickname)) {
			throw new UserNicknameAlreadyExistsException(nickname);
		}
	}

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
