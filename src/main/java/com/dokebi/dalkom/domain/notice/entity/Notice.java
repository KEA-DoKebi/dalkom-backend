package com.dokebi.dalkom.domain.notice.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeSeq;

    @ManyToOne
    @JoinColumn(name = "adminSeq")
    private Admin admin;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "state", nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
    private String state;
}
