package com.dokebi.dalkom.domain.admin.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.inqury.entity.inquiry;
import com.dokebi.dalkom.domain.notice.entity.notice;
import com.dokebi.dalkom.domain.stock.entity.prdtStkHistory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class admin extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminSeq;

    @Column(name = "adminId", nullable = false, unique = true)
    private String adminId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "depart", nullable = false)
    private String depart;


    @OneToMany(mappedBy = "admin")
    private List<inquiry> inquiry = new ArrayList<>();

    @OneToMany(mappedBy = "admin")
    private List<notice> notice = new ArrayList<>();

}
