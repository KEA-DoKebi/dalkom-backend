package com.dokebi.dalkom.domain.cart.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.user.entity.User;
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
public class OrderCart extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordrCartSeq;

    @ManyToOne
    @JoinColumn(name = "productSeq")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private User user;

    @Column(name = "optionDetail")
    private String optionDetail;

    @Column(name = "amount", nullable = false)
    private Integer amount;

}
