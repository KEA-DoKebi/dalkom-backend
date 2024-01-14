package com.dokebi.dalkom.domain.stock.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.option.entity.PrdtOption;
import com.dokebi.dalkom.domain.product.entity.Product;
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
@Table(name = "prdtStock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrdtStock extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prdtStockSeq;

    @ManyToOne
    @JoinColumn(name = "productSeq")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "prdtOptionSeq")
    private PrdtOption prdtOption;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "prdtStock")
    private List<PrdtStkHistory> prdtStkHistory = new ArrayList<>();

}
