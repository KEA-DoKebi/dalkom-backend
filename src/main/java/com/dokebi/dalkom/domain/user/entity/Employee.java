package com.dokebi.dalkom.domain.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "employee", schema = "dkt")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

    @Id
    private String empId;

    private String email;

    private String name;

    private String joinedAt;

}
