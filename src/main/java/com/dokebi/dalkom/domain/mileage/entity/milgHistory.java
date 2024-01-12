package com.dokebi.dalkom.domain.mileage.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.user.entitiy.user;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "milgHistory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class milgHistory extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milgHistorySeq;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private user user;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "balance", nullable = false)
    private Integer balance;

    @Column(name = "type", nullable = false)
    private String type;

}
