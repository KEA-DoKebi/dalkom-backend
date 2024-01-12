package com.dokebi.dalkom.domain.order.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.option.entity.prdtOption;
import com.dokebi.dalkom.domain.product.entity.product;
import com.dokebi.dalkom.domain.review.entity.review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ordrDetail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ordrDetail extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordrDetailSeq;

    @ManyToOne
    @JoinColumn(name = "ordrSeq")
    private ordr ordr;

    @ManyToOne
    @JoinColumn(name = "productSeq")
    private product product;

    @ManyToOne
    @JoinColumn(name = "prdtOptionSeq")
    private prdtOption prdtOption;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "price", nullable = false)
    private Integer price;

    @OneToOne(mappedBy = "ordrDetail")
    private review review;
}
