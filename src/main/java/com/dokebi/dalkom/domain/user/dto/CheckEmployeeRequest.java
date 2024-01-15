package com.dokebi.dalkom.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckEmployeeRequest {
    private String empId;
    private String name;
    private String email;
    private String joinedAt;
}
