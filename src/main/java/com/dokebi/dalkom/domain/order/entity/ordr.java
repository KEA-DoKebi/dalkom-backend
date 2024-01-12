package com.dokebi.dalkom.domain.order.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.cart.entity.ordrCart;
import com.dokebi.dalkom.domain.user.entitiy.user;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ordr")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ordr extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordrSeq;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private user user;

    @Column(name = "rcvrName", nullable = false)
    private String receiverName;

    @Column(name = "rcvrAddress", nullable = false)
    private String receiverAddress;

    @Column(name = "rcvrMobileNum", nullable = false)
    private String receiverMobileNum;

    @Column(name = "rcvrMemo", nullable = false)
    private String receiverMemo;

    @Column(name = "totalPrice", nullable = false)
    private Integer totalPrice;

    @Column(name = "ordrState", nullable = false)
    private String ordrState;

    @OneToMany(mappedBy = "ordr")
    private List<ordrDetail> ordrDetail = new ArrayList<>();

}
