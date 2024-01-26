package com.dokebi.dalkom.domain.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.admin.dto.AdminDto;
import com.dokebi.dalkom.domain.admin.dto.CreateAdminRequest;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.exception.AdminNotFoundException;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;
import com.dokebi.dalkom.domain.user.exception.UserNicknameAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	public Page<AdminDto> readAdminList(Pageable pageable) {
		List<Admin> adminList = adminRepository.findAll();
		List<AdminDto> adminDtoList = new ArrayList<>();
		for (Admin admin : adminList) {
			AdminDto adminDto = AdminDto.toDto(admin);
			adminDtoList.add(adminDto);
		}

		return new PageImpl<>(adminDtoList, pageable, adminDtoList.size());
	}

	@Transactional
	public void createAdmin(CreateAdminRequest request) {
		validateNickname(request.getNickname());

		// 비밀번호 암호화
		String password = passwordEncoder.encode(request.getPassword());
		request.setPassword(password);
		adminRepository.save(CreateAdminRequest.toEntity(request));

	}

	public Admin readAdminByAdminSeq(Long adminSeq) {
		return adminRepository.findByAdminSeq(adminSeq).orElseThrow(AdminNotFoundException::new);
	}

	public Admin readAdminByAdminId(String adminId) {
		return adminRepository.findByAdminId(adminId).orElseThrow(AdminNotFoundException::new);
	}

	public Page<AdminDto> readAdminListSearch(String adminId, String name, String nickname, Pageable pageable) {
		Page<Admin> adminList = adminRepository.findAdminListBySearch(adminId, name, nickname, pageable);
		List<AdminDto> adminDtoList = new ArrayList<>();
		for (Admin admin : adminList) {
			AdminDto adminDto = AdminDto.toDto(admin);
			adminDtoList.add(adminDto);
		}

		return new PageImpl<>(adminDtoList, pageable, adminDtoList.size());
	}

	private void validateNickname(String nickname) {
		if (adminRepository.existsByNickname(nickname)) {
			throw new UserNicknameAlreadyExistsException(nickname + "은 이미 사용중입니다.");
		}
	}
}
