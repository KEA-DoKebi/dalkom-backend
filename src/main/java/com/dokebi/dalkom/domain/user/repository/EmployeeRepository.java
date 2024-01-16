package com.dokebi.dalkom.domain.user.repository;

import com.dokebi.dalkom.domain.user.dto.CheckEmployeeResponse;
import com.dokebi.dalkom.domain.user.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Employee findAllByEmpId(String empId);
}
