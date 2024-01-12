package com.dokebi.dalkom.domain.stock.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.cart.entity.ordrCart;
import com.dokebi.dalkom.domain.option.entity.prdtOption;
import com.dokebi.dalkom.domain.product.entity.prdtImage;
import com.dokebi.dalkom.domain.product.entity.product;
import com.dokebi.dalkom.domain.user.entitiy.user;
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
public class prdtStock extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prdtStockSeq;

    @ManyToOne
    @JoinColumn(name = "productSeq")
    private product product;

    @ManyToOne
    @JoinColumn(name = "prdtOptionSeq")
    private prdtOption prdtOption;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "prdtStock")
    private List<prdtStkHistory> prdtStkHistory = new ArrayList<>();

}
