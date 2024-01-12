package com.dokebi.dalkom.domain.cart.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.product.entity.product;
import com.dokebi.dalkom.domain.user.entitiy.user;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ordrCart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ordrCart extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordrCartSeq;

    @ManyToOne
    @JoinColumn(name = "productSeq")
    private product product;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private user user;

    @Column(name = "optionDetail")
    private String optionDetail;

    @Column(name = "amount", nullable = false)
    private Integer amount;

}
