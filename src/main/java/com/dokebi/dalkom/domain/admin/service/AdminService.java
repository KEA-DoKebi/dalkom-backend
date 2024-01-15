package com.dokebi.dalkom.domain.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminRepository adminRepository;

	public List<Admin> readAll() {
		return adminRepository.findAll();
	}
}
