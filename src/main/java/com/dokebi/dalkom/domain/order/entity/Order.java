package com.dokebi.dalkom.domain.order.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.user.entitiy.User;
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
@Table(name = "ordr")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderSeq;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private User user;

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

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> ordrDetail = new ArrayList<>();

}
