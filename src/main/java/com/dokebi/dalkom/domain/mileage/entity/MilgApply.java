package com.dokebi.dalkom.domain.mileage.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.user.entitiy.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "milgApply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MilgApply extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milgApplySeq;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private User user;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "approvedState", nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
    private String approveState;

    @Column(name = "approvedAt")
    private LocalDateTime approvedAt;

}
