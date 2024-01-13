package com.dokebi.dalkom.domain.review.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.order.entity.OrdrDetail;
import com.dokebi.dalkom.domain.user.entitiy.User;
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
public class Review extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewSeq;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private User user;

    @OneToOne
    @JoinColumn(name = "orderDetailSeq")
    private OrdrDetail ordrDetail;

    @Column(name = "content", nullable = false)
    private Long content;

    @Column(name = "rating", nullable = false)
    private Long rating;
}
