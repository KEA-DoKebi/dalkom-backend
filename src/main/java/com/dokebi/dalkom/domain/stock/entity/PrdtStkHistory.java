package com.dokebi.dalkom.domain.stock.entity;

import com.dokebi.dalkom.common.EntityDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "prdtStkHistory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrdtStkHistory extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prdtStkHistorySeq;

    @ManyToOne
    @JoinColumn(name = "prdtStockSeq")
    private PrdtStock prdtStock;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "amountChanged", nullable = false)
    private Integer amountChanged;
}
