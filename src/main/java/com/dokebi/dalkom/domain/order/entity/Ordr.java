package com.dokebi.dalkom.domain.order.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.user.entity.User;
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
public class Ordr extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordrSeq;

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

    @OneToMany(mappedBy = "ordr")
    private List<OrdrDetail> ordrDetail = new ArrayList<>();

}