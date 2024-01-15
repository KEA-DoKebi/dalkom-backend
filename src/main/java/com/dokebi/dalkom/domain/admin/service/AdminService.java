package com.dokebi.dalkom.domain.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.admin.dto.AdminDto;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminRepository adminRepository;

	public List<AdminDto> readAll() {
		List<Admin> adminList = adminRepository.findAll();
		List<AdminDto> adminDtoList = new ArrayList<>();
		for (Admin admin : adminList) {
			AdminDto adminDto = AdminDto.toDto(admin);
			adminDtoList.add(adminDto);
		}
		return adminDtoList;
	}
}
