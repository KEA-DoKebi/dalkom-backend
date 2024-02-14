package com.dokebi.dalkom.domain.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.domain.admin.dto.AdminSearchCondition;
import com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse;

public interface AdminRepositoryCustom {
	Page<ReadAdminResponse> searchAdmin(AdminSearchCondition condition, Pageable pageable);

}
