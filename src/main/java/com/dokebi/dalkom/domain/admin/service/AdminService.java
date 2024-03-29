package com.dokebi.dalkom.domain.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.admin.dto.AdminDashboardResponse;
import com.dokebi.dalkom.domain.admin.dto.CreateAdminRequest;
import com.dokebi.dalkom.domain.admin.dto.MonthlyProductListDto;
import com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.exception.AdminNotFoundException;
import com.dokebi.dalkom.domain.admin.mapper.AdminMapper;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;
import com.dokebi.dalkom.domain.user.dto.SignUpRequest;
import com.dokebi.dalkom.domain.user.entity.Employee;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.EmployeeNotFoundException;
import com.dokebi.dalkom.domain.user.exception.UserEmailAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNicknameAlreadyExistsException;
import com.dokebi.dalkom.domain.user.repository.EmployeeRepository;
import com.dokebi.dalkom.domain.user.repository.UserRepository;
import com.dokebi.dalkom.domain.user.service.SignService;

@Service
@Transactional(readOnly = true)
public class AdminService {
	private final AdminRepository adminRepository;
	private final AdminMapper adminMapper;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final EmployeeRepository employeeRepository;
	private final SignService signService;

	public AdminService(AdminRepository adminRepository, AdminMapper adminMapper, PasswordEncoder passwordEncoder,
		UserRepository userRepository,
		EmployeeRepository employeeRepository, @Lazy SignService signService) {
		this.adminRepository = adminRepository;
		this.adminMapper = adminMapper;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.employeeRepository = employeeRepository;
		this.signService = signService;
	}

	// ADMIN-001 (유저 비활성화)
	@Transactional
	public void updateUser(Long userSeq) {
		Optional<User> optionalUser = userRepository.findById(userSeq);
		optionalUser.ifPresent(user -> {
			// 값이 존재할 때만 실행됨
			user.setState("N");
		});
	}

	// ADMIN-006 (관리자 생성)
	@Transactional
	public void createAdmin(CreateAdminRequest request) {
		validateNickname(request.getNickname());

		// 비밀번호 암호화
		String password = passwordEncoder.encode(request.getPassword());
		request.setPassword(password);
		adminRepository.save(CreateAdminRequest.toEntity(request));
	}

	// ADMIN-007 (관리자 목록 조회)
	public Page<ReadAdminResponse> readAdminList(Pageable pageable) {
		return adminRepository.findAllAdminList(pageable);
	}

	// ADMIN-008 (관리자 유저 생성)
	@Transactional
	public void createUser(SignUpRequest request) {
		// 임직원 테이블에 입력한 정보가 있는지 확인
		checkEmployee(request);

		// 이메일, 닉네임 중복성 검사
		validateSignUpInfo(request);

		// 비밀번호 암호화
		String password = passwordEncoder.encode(request.getPassword());
		request.setPassword(password);

		//마일리지 추가
		request.setMileage(signService.calculateMileage(request.getJoinedAt()));

		// 회원 정보 저장
		userRepository.save(SignUpRequest.toEntity(request));
	}

	// ADMIN-009 (관리자 대시보드)
	public AdminDashboardResponse readDashboard() {

		AdminDashboardResponse response = new AdminDashboardResponse();
		response.setTotalMileage(adminMapper.findTotalPrice());
		response.setTotalMonthlyMileage(adminMapper.findTotalMonthlyPrice());
		response.setTotalDailyMileage(adminMapper.findTotalDailyPrice());
		response.setMonthlyPriceList(adminMapper.findMonthlyPriceList());
		response.setMonthlyCategoryList(adminMapper.findMonthlyCategoryList());

		int offset = 0;
		int limit = 5;
		List<MonthlyProductListDto> monthlyProductList = adminMapper.findMonthlyProductList(offset, limit);

		int total = adminMapper.countMonthlyProductList();
		Page<MonthlyProductListDto> page = new PageImpl<>(monthlyProductList, PageRequest.of(0, 5), total);

		response.setMonthlyProductList(page);

		return response;
	}

	public Admin readAdminByAdminSeq(Long adminSeq) {
		return adminRepository.findByAdminSeq(adminSeq).orElseThrow(AdminNotFoundException::new);
	}

	public Admin readAdminByAdminId(String adminId) {
		return adminRepository.findByAdminId(adminId).orElseThrow(AdminNotFoundException::new);
	}

	// ADMIN-010 (관리자 목록 조회 검색)
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

	private void validateNickname(String nickname) {
		if (adminRepository.existsByNickname(nickname)) {
			throw new UserNicknameAlreadyExistsException(nickname);
		}
	}

	protected void checkEmployee(SignUpRequest request) {
		Employee employee = employeeRepository.findByEmpId(request.getEmpId())
			.orElseThrow(EmployeeNotFoundException::new);

		if (!employee.getName().equals(request.getName()) ||
			!employee.getEmail().equals(request.getEmail()) ||
			!employee.getJoinedAt().equals(request.getJoinedAt())) {
			throw new EmployeeNotFoundException();
		}
	}

	// 이메일, 닉네임 중복성 검사
	protected void validateSignUpInfo(SignUpRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UserEmailAlreadyExistsException(request.getEmail());
		}
		if (userRepository.existsByNickname(request.getNickname())) {
			throw new UserNicknameAlreadyExistsException(request.getNickname());
		}
	}
}
