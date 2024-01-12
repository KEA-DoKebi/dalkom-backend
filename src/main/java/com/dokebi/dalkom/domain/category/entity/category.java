package com.dokebi.dalkom.domain.category.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.cart.entity.ordrCart;
import com.dokebi.dalkom.domain.inqury.entity.inquiry;
import com.dokebi.dalkom.domain.product.entity.product;
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
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class category extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categorySeq;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parentSeq")
    private Long parentSeq;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "category")
    private List<product> product = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<inquiry> inquiry = new ArrayList<>();
}
