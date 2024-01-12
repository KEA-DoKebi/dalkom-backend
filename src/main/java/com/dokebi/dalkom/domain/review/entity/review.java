package com.dokebi.dalkom.domain.review.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.order.entity.ordrDetail;
import com.dokebi.dalkom.domain.user.entitiy.user;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class review extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewSeq;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private user user;

    @OneToOne
    @JoinColumn(name = "orderDetailSeq")
    private ordrDetail ordrDetail;

    @Column(name = "content", nullable = false)
    private Long content;

    @Column(name = "rating", nullable = false)
    private Long rating;
}
